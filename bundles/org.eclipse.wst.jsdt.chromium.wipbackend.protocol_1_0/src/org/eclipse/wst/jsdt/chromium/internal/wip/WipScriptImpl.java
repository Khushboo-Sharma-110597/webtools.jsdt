// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip;

import java.util.List;

import org.eclipse.wst.jsdt.chromium.RelayOk;
import org.eclipse.wst.jsdt.chromium.Script;
import org.eclipse.wst.jsdt.chromium.SyncCallback;
import org.eclipse.wst.jsdt.chromium.internal.ScriptBase;
import org.eclipse.wst.jsdt.chromium.internal.liveeditprotocol.LiveEditProtocolParserAccess;
import org.eclipse.wst.jsdt.chromium.internal.liveeditprotocol.LiveEditResult;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.CallFrameValue;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.SetScriptSourceData;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger.SetScriptSourceParams;
import org.eclipse.wst.jsdt.chromium.util.GenericCallback;
import org.eclipse.wst.jsdt.chromium.util.RelaySyncCallback;

/**
 * Wip implementation of {@link Script}.
 */
class WipScriptImpl extends ScriptBase<String> {
  private final WipScriptManager scriptManager;

  WipScriptImpl(WipScriptManager scriptManager, Descriptor<String> descriptor) {
    super(descriptor);
    this.scriptManager = scriptManager;
  }

  @Override
  public RelayOk setSourceOnRemote(String newSource, UpdateCallback callback,
      SyncCallback syncCallback) {
    return sendLiveEditRequest(newSource, false, callback, syncCallback);
  }

  @Override
  public RelayOk previewSetSource(String newSource, UpdateCallback callback,
      SyncCallback syncCallback) {
    return sendLiveEditRequest(newSource, true, callback, syncCallback);
  }

  private RelayOk sendLiveEditRequest(String newSource, final boolean preview,
      final UpdateCallback updateCallback,
      final SyncCallback syncCallback) {

    RelaySyncCallback relay = new RelaySyncCallback(syncCallback);
    final RelaySyncCallback.Guard guard = relay.newGuard();

    SetScriptSourceParams params = new SetScriptSourceParams(getId(), newSource, preview);

    GenericCallback<SetScriptSourceData> commandCallback =
        new GenericCallback<SetScriptSourceData>() {
      @Override
      public void success(SetScriptSourceData value) {
        RelayOk relayOk =
            possiblyUpdateCallFrames(preview, value, updateCallback, guard.getRelay());
        guard.discharge(relayOk);
      }

      @Override
      public void failure(Exception exception) {
        updateCallback.failure(exception.getMessage(), Failure.UNSPECIFIED);
      }
    };

    WipCommandProcessor commandProcessor = scriptManager.getTabImpl().getCommandProcessor();
    return commandProcessor.send(params, commandCallback, guard.asSyncCallback());
  }

  private RelayOk possiblyUpdateCallFrames(boolean preview, final SetScriptSourceData data,
      final UpdateCallback updateCallback, RelaySyncCallback relay) {

    // TODO: support 'step-in recommended'.

    List<CallFrameValue> callFrames = null;
    if (!preview) {
      callFrames = data.callFrames();
    }
    if (callFrames == null) {
      dispatchResult(data.result(), updateCallback);
      return relay.finish();
    } else {
      GenericCallback<Void> setFramesCallback =
          new GenericCallback<Void>() {
        @Override public void success(Void value) {
          dispatchResult(data.result(), updateCallback);
        }
        @Override public void failure(Exception exception) {
          throw new RuntimeException(exception);
        }
      };
      WipContextBuilder contextBuilder = scriptManager.getTabImpl().getContextBuilder();
      return contextBuilder.updateStackTrace(callFrames, setFramesCallback,
          relay.getUserSyncCallback());
    }
  }

  private void dispatchResult(SetScriptSourceData.Result result, UpdateCallback updateCallback) {
    if (updateCallback != null) {
      LiveEditResult liveEditResult;
      try {
        liveEditResult =
            LiveEditProtocolParserAccess.get().parseLiveEditResult(result.getUnderlyingObject());
      } catch (JsonProtocolParseException e) {
        throw new RuntimeException("Failed to parse LiveEdit response", e);
      }
      ChangeDescription wrappedChangeDescription =
          UpdateResultParser.wrapChangeDescription(liveEditResult);
      updateCallback.success(false, null, wrappedChangeDescription);
    }
  }
}

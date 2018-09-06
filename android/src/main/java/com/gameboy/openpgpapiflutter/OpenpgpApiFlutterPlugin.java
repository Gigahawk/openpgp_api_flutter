package com.gameboy.openpgpapiflutter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import org.openintents.openpgp.util.OpenPgpAppPreference;
import org.openintents.openpgp.util.OpenPgpKeyPreference;
import org.openintents.openpgp.IOpenPgpService2;
import org.openintents.openpgp.OpenPgpDecryptionResult;
import org.openintents.openpgp.OpenPgpError;
import org.openintents.openpgp.OpenPgpSignatureResult;
import org.openintents.openpgp.util.OpenPgpApi;
import org.openintents.openpgp.util.OpenPgpServiceConnection;
import org.openintents.openpgp.util.OpenPgpUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static java.security.AccessController.getContext;


/**
 * OpenpgpApiFlutterPlugin
 */
public class OpenpgpApiFlutterPlugin implements MethodCallHandler {
  /**
   * Plugin registration.
   */

  private final Registrar registrar;
  private Result result;

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "openpgp_api_flutter");
    OpenpgpApiFlutterPlugin openpgpApiFlutterPlugin = new OpenpgpApiFlutterPlugin(registrar);
    channel.setMethodCallHandler(openpgpApiFlutterPlugin);
  }

  private OpenpgpApiFlutterPlugin(Registrar registrar) {
      this.registrar = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
      switch (call.method) {
          case "getProvidersList":
              result.success(getProvidersList());
              break;
          default:
              result.notImplemented();
              break;
      }
  }

  private ArrayList<String> getProvidersList() {
      ArrayList<String> providerList = new ArrayList<>();

      Intent intent = new Intent(OpenPgpApi.SERVICE_INTENT_2);
      List<ResolveInfo> resInfo = registrar.activity().getPackageManager().queryIntentServices(intent, 0);
      if (resInfo != null) {
          for (ResolveInfo resolveInfo : resInfo) {
              if (resolveInfo.serviceInfo == null) {
                  continue;
              }

              providerList.add(resolveInfo.serviceInfo.packageName);
          }
      }

      return providerList;
  }
}

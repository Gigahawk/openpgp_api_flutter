import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class OpenpgpApiFlutter {
  static const MethodChannel _channel =
      const MethodChannel('openpgp_api_flutter');

  static Future<List> getProvidersList() async {
    final List providers_list = await _channel.invokeMethod('getProvidersList');

    providers_list.forEach((provider) => debugPrint(provider.toString()));
    debugPrint(providers_list.length.toString());
    return providers_list;
  }
}

import 'package:flutter/material.dart';
class SecondFlutterWidget extends StatefulWidget{
  _ContentWidgetState createState() => new _ContentWidgetState();
}
class _ContentWidgetState extends State<SecondFlutterWidget>{
  @override
  
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter页面'),
      ),
      body: Center(
        child: Center(child: Text('第二个Flutter页面'),),
      )
    );
  }
}
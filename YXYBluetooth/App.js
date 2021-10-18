/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Componet } from 'react';

import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import { BleManager } from './src/BleManager';



class App extends React.Component {

  constructor(props) {
    super(props);
    this.bleManager = new BleManager();
    this.bleManager.onStateChange((state) => {
      console.log("===========state=======", state)
    }, true);

  }

  componentDidMount() {

  }



  render() {
    return (<View style={{
      flex: 1,
      backgroundColor: "#fff",
      justifyContent: 'center',
      alignItems: 'center'
    }}>
      <Text style={{ color: "blue", fontSize: 30 }}>蓝牙库</Text>
    </View>);
  }

}

export default App;

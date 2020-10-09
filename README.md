
# react-native-video-compress-test-lib

## Getting started

`$ npm install react-native-video-compress-test-lib --save`

### Mostly automatic installation

`$ react-native link react-native-video-compress-test-lib`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-video-compress-test-lib` and add `RNVideoCompressTestLib.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNVideoCompressTestLib.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNVideoCompressTestLibPackage;` to the imports at the top of the file
  - Add `new RNVideoCompressTestLibPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-video-compress-test-lib'
  	project(':react-native-video-compress-test-lib').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-video-compress-test-lib/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-video-compress-test-lib')
  	```


## Usage
```javascript
import RNVideoCompressTestLib from 'react-native-video-compress-test-lib';

Add android:requestLegacyExternalStorage="true" in your AndroidManifest.xml file. like :

<application
  ...
  android:requestLegacyExternalStorage="true"
  .... >
  
 Add these line of dependency statements in your package.json file
 
 "devDependencies": {
  ...
  "react-native-video-compress-test-lib": "1.0.1"
  ...
  }

RNVideoCompressTestLib;
```
Due to high resolution on modern smart phone camera, video and image size are increasing day-by-day. So it is very difficult to share on social platforms, thus we need to compress the file size. Silli compressor is based on mp4parser : isoParser.

Inside your Project:

import {NativeModules} from from 'react-native';
const {CompressModule} = NativeModules;

const EVENT_NAME = new NativeEventEmitter(NativeModules.CompressModule);

//Percentage completion Emitter
this.subscription = EVENT_NAME.addListener('EVENT_TAG', ({value})=> {  
                                       console.log(value + "% completed");  
                                        });
                                        

// fileName :- output file name
// size :- output file size
// path :- output file path
// message :- conversion success message with file details

var successCall = (fileName, size, path, message) => {
      ToastModule.showToast(message);
      
      //remove emitter subscription after convertion success
      this.subscription.remove();
      
      console.log(message);
    };
    
var errorCall = (errorMessage) => {
      console.log(errorMessage);
    };
    
//Methode to start compression task
// inputString :- inpot file path with extension -> '/storage/emulated/0/Download/20201003_095653.mp4'
// errorCall, SuccessCall :- Callback methode
CompressModule.compressVideo(inputString, errorCall, successCall);




  

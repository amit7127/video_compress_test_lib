
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

// TODO: What to do with the module?
RNVideoCompressTestLib;
```
  
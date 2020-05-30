# IP Camera Plugin

For OBS Scene Queue

_By Samuel-Anton Jansen_

**Note: this version is very limited; you cannot edit camera connection settings yet**

**Note: only limited IP camera's are supported.** You can add support for your camera type by creating a pull request for this GitHub repository. Create a new class in `cameras/` which implements the `Camera` interface. Add the new camera class to `IpCameraPlugin.cameras`.

### How to use

1. Select your camera
1. Presets will be loaded from your camera
1. Drag and drop the presets in the queue where you want it

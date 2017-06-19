# ShardDemo
Demo application for CVForge Shards.
Contains a sample Shard and a class for usage and protoyping outside of ImageJ.
All Shards must implement the CVForgeShard interface and contain only static methods.
Export the created Shard as a jar and give it the file extension ".shard.jar" to allow CVForge to recognize it.

Watch the related Demo video on Youtube: https://www.youtube.com/watch?v=PDQCJ47GEAw


Good practices for Shard creation:
* OpenCV should be your only dependency.
* Though not stricly forbidden, avoid adding the ImageJ package to your dependencies.
* Do not use any third-party dependencies! If you do so, you will have to ship them with your Shard!
* Keep in mind that Shards are meant to be collections of processing/analysis methods! If your code grows beyond this scale and/or requires other packages, you should consider writing your own ImageJ plugin!

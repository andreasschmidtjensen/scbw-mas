## StarCraft: Brood War in Multi-Agent Systems

This project creates a bridge between [BWAPI](https://code.google.com/p/bwapi/ "BWAPI") for [StarCraft: Brood War](http://us.blizzard.com/en-us/games/sc/ "StarCraft: Brood War") and EIS-enabled Multi-Agent Systems. 

The environment interface standard ([EIS](https://github.com/eishub/eis/wiki "EIS")) has been developed to facilitate connecting software agents to environments. 

### Requirements
* 32-bit Java (required by the JNI-interface that connects to StarCraft: Brood War)
* StarCraft: Brood War
* An EIS-compatible Multi-Agent System

### Quick Setup
1. Install StarCraft: Brood War and update to version 1.16.1.
3. Download this project (Download ZIP in the right-side menu).
4. Install the JNI BWAPI Starter Pack from the StarterPack-folder.
5. Run one of the examples in the examples-folder.
6. Run SCBW through ChaosLauncher with the BWAPI plugin enabled.
7. Start a new game using Terran as player and watch the agents play!

### Current status

- Most units can be controlled by the MAS.
- Only a subset of the Terran units have had implemented special abilities:
	- Marine (Stimpacks)

### Project overview
The project contains the following folders:

* **EISBW:** The EIS implementation of BWAPI. 
* **examples:** A collection of examples for different multi-agent systems (currently Jason and GOAL).
* **libs:** Jar-files required for building the interface and running the various examples.
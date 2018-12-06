Incorporeal
===========

A Botania addon that adds more toys for your corporea system. Designed for managing large-scale systems and expanding what is possible with corporea without trivializing it.

Development status - first version is out, now thinking of more things to add to it ;)

## Sounds cool what does it add

* A Corporea Spark Tinkerer to swap out spark networks
* An Item Frame Tinkerer to switch items on Item Frames
* Corporea Prevaricator to lie to your system about the contents of a chest
* Corporea Solidifier to store corporea requests as *corporea tickets*
* Ticket Conjurer to create tickets on the go
* Sanvocalia flower to read those tickets off to corporea indexes
* the Rod of the Fractured Space: teleport items to an Open Crate, from anywhere
* the Ender Soul Core: A link to a player's ender chest that can be hoppered into, corporea sparked, etc
* the Corporea Soul Core: Only people who have a soul core on the network can use corporea indices
* Plant redstone roots to grow natural redstone circuitry
* Corporea Retainer Evaporator, allowing for richer control of interceptor-retainer pairs
* Lexica Botania integration for ~~all~~most of this, of course

* 1 coremod boio

### What doesn't it add?

* GUIs, what is this, some garbage tech mod?
* Things that make corporea systems easy to create

### What's left to do?

* Hire a goddamn artist lol

### What's planned for the next update?

* ~~Corporea spark augment x d~~too hard and hacky, pr that
* More options for corporea sparks?
* "Data boxes"
* The little worldgen structure thingie.
* MORE COREMODDING????!??!!

## Build instructions

Nothing out of the ordinary.

However, the buildscript also contains a few handy lines that allow you to set your Minecraft username and UUID when you launch your dev instance. This is useful for debugging things like soul cores. You must set neither or both, or else Minecraft will get really confused.

* Discover your Minecraft UUID using a tool like https://mcuuid.net/
* Create a file called `gradle.properties` in your Gradle home directory (on Windows at least, it's in `C:/Users/<username>/.gradle`)
* Format the file like this:

    mc_uuid=873dea16-d058-4343-861c-f62c21da124b
    mc_username=quaternary

* Save, and launch the game through the usual methods

## License information

**All source files in `src/main/java/quaternary/incorporeal/` except `SpookyClassWriter.java`:**

This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

**`SpookyClassWriter.java`:**

This is from Gotta Go Fast by Thiakil, MIT license. Its license is reproduced in OTHER_LICENCES.md.

**All files in `src/main/java/quaternary/incorporealapi/`:**

CC0, public domain. Repackaging them in your mods is very much encouraged.
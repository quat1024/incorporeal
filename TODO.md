Todo List for Cygnus
====================

Stuff I'm likely to forget about + stuff I need to do. Don't release without these checked off.

* Finish the models and textures of course.

* Implement cross-type request shoving, probably on Botania's end
  * Ok so this is kind of a weird problem.
  * Cygnus funnels can make indices request itemstacks, and funnels request strings
  * ...which they can't normally do
  * ...by shoving a request of the "wrong" type into a corporea retainer and pulsing it.
  * The problem is that they typecheck the corporea matcher in `doCorporeaRequest` and earlyexit if it's the wrong type.
  * They don't actually have to, though, is the thing!
  * Both functions just pass the buck on to `CorporeaHelper.doCorporeaRequest` which can take both stacks and strings.
  * Neither function does anything else with the argument.
  * So it's possible to just remove the typecheck, and cross-type request shoving works just fine.
  
* Implement funnels-requesting-strings...?
  * This is useful.
  * Basically just if the funnel has a renamed item, it requests the string containing the item's name.
  * This lets you do wildcards and stuff from a corporea funnel.
  * Problem: you can request * from a corporea funnel which is not supposed to be possible.

* Think over the fuzzy-match funnel and crystal cube and stuff.
  * The problem dud is trying to solve is that ticket *processing* through the ender soul core is silly.
  * Simply because there's not a way to say "request all tickets from my ender chest".
  * Tickets are all very different, and while it's possible to detect *specific* tickets due to stackability, *all* tickets is not possible.
  * A fuzzy-match funnel would match all items regardless of NBT.
  * Problem: Botania can't easily extend the idea of corporea requests. (yet?)
  * Other problem: Currently Dud is using string ticket shenanigans to wildcard out a "corporea ticket?" request. This seems to work ok, and I kind of like the shenanigans and don't want to get rid of them.
    * but at the same time I don't want players to have to care about "is this an item request or string request", lol
    * Cygnus would make this easier since it adds another way of replaying the request.

* Do a usability pass.
  * Players should be able to find out lots of important information based on just the Manaseer Monocle, wand of the forest overlays, you know, stuff like that. No /blockdata even though I use it all the time for testing xddddd.

* A looks pass for the master cygnus spark (which is very temp right now) and the retainer
  * Probably should delegate drawing to the cygnus datatype instead of just doing toString and rendering that lol.

* Crafting recipes!!!
  * I still haven't thought about how to obtain cygnus sparks!
  * I like the idea of launching regular corporea sparks past the height limit of the world. Lots of funny ideas to automate that (slime blocks, influence lenses, etc)
    * dress it up as "voidtouching"
    * maybe you get more the higher they go? lol
  * Other than that, botania-style recipes where it's like "dropper + spark" are always good

* More conveniences
  * Manually putting numbers on the stack is fuckin annoying. You have to make a corporea request for something and like, hook up a whole corporea network and stuff
  * I like the idea of numbers being hard to reach automatically, but a ticket conjurer-like would be really handy either way.

* Playtest lol???
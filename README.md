# Tank-Game

## Description
This is a JavaFX project for a playable tank game.

The main file for the game loop is located at `/src/game/TankGameLoop.java`

## Controls
The WASD keys are used to control the movement of the tank. The shooting can be configured for mouse input or for space bar shooting. For space bar shooting, the projectiles are shot directly in front of the player tank. For mouse input shooting, the projectiles are shot towards where the user is aiming the mouse. The number keys are used to switch between weapons that are available.

## Code Structure
The classes used are meant for maximum flexibility for adding features or changing existing features. Any object within the scene extends the Sprite class, which contains a node, position, and velocity. By using inheritance for projectiles, tanks, and wall objects, it is extremely easy to change the attributes or behaviour of any object in the game.

- The `Projectile` class represents any object that is fired from a `Weapon`. This includes moving bullets as well as mines that sit for some time before exploding. `Projectile` objects can also have the option of exploding into multiple child projectiles of a different type. For example, `ExplodingBullet` will split into a number of small bullets after exploding.
- The `Weapon` class represents a weapon that is used to fire a `Projectile` object. It has a rate of fire, capacity, and ammo, which limit how often and how many projectiles can be fired from the weapon.
- The `WeaponRack` defines a list of `Weapon` objects. The user can use the number keys to switch between weapons in the weapon rack.


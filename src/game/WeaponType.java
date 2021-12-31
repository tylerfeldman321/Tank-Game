package game;

public class WeaponType {
    public static final Weapon BASIC = new Weapon(null, 0,
            ProjectileType.BASIC_BULLET, 10, 10, 100, 1, 3,
            5, false);
    public static final Weapon SHOTGUN = new Weapon(null, 0,
            ProjectileType.SMALL_BULLET, 1, 2, 10, 5, 4,
            40, false);
    public static final Weapon EXPLOSIVE_LAUNCHER = new Weapon(null,
            0, ProjectileType.EXPLODING_BULLET, 1, 1, 10,
            1, 3, 10, false);
    public static final Weapon MACHINE_GUN = new Weapon(null, 0,
            ProjectileType.SMALL_BULLET, 8, 10, 30, 1, 3,
            30, true);
}



package game;

public class WeaponType {
    public static final Weapon BASIC = new Weapon(null, 0,
            ProjectileType.BASIC_BULLET, 10, 10, 100, 1, 3,
            5);
    public static final Weapon SHOTGUN = new Weapon(null, 0,
            ProjectileType.SMALL_BULLET, 10, 10, 100, 30, 5,
            20);
    public static final Weapon EXPLOSIVE_LAUNCHER = new Weapon(null,
            0, ProjectileType.EXPLODING_BULLET, 10, 10, 100,
            1, 3, 10);
}



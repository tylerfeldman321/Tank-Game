package game;

import engine.GameWorld;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponRack {

    private GameWorld gameWorld;
    private Tank owner;
    private Map<Integer, Weapon> weaponRack = new HashMap<>();
    private int currentWeaponIndex = 1;
    private int startingIndex = 1;

    public WeaponRack(GameWorld gameWorld, Tank owner, List<Weapon> weaponList) {
        this.gameWorld = gameWorld;
        this.owner = owner;
        for (int i = 0; i < weaponList.size(); i++) {
            Weapon weapon = weaponList.get(i);
            addWeapon(i + startingIndex, weapon);
        }
    }

    public WeaponRack(GameWorld gameWorld, Tank owner, Weapon... weapons) {
        this(gameWorld, owner, Arrays.asList(weapons));
    }

    public int addWeapon(Weapon weapon) {
        int index = startingIndex;
        while (true) {
            if (!weaponRack.containsKey(index)) {
                addWeapon(index, weapon);
                return index;
            }
            index++;
        }
    }

    public void addWeapon(int index, Weapon weapon) {
        weapon.setGameWorldAndOwnerRadius(gameWorld, owner.tankRadius);
        weaponRack.put(index, weapon);
    }

    public Weapon getWeapon(int index) {
        if (weaponRack.containsKey(index)) {
            return weaponRack.get(index);
        }
        return null;
    }

    public void swapWeapon(int index) {
        if (weaponRack.containsKey(index)) {
            currentWeaponIndex = index;
        }
    }

    private Weapon getCurrentWeapon() {
        return weaponRack.get(currentWeaponIndex);
    }

    public void fire(double angleDegrees) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon != null) {
            currentWeapon.fire(angleDegrees, owner.node.getTranslateX(), owner.node.getTranslateY());
        }
    }

    public void fire(double x, double y) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon != null) {
            currentWeapon.fire(x, y, owner.node.getTranslateX(), owner.node.getTranslateY());
        }
    }

    public boolean currentWeaponIsRapidFire() {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon != null) {
            return getCurrentWeapon().isRapidFire();
        }
        return false;
    }
}

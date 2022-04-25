package Lesson4;

import java.util.Random;
import java.util.Scanner;

public class MyRace {
    public static Random random = new Random();
    public static Scanner sc = new Scanner(System.in);

    public static String riderName = sc.nextLine();
    public static char rider = '$';
    public static int riderHealth = 100;
    public static int riderPosX;
    public static int riderPosY;
    public static final int rideMoveDown = 2;
    public static final int riderMoveLeft = 1;
    public static final int riderMoveRight = 3;


    public static char policeCar = '#';
    public static int policeCarPower = 50;
    public static int policeCarCount;

    public static char finish = '~';
    public static int finishCount;

    public static char heal = '+';
    public static int healNumber = 25;
    public static int healCount;


    public static int mapWidth = 5;
    public static int mapHeight = 10;
    public static char[][] map;
    public static char emptyCell = '_';


    public static void main(String[] args){
        Name();
        createMap();

        spawnRider();
        spawnHeal();
        spawnFinish();
        spawnPoliceCar();

        while (true) {
            showMap();
            riderMoveAction();

            if (!isRiderAlive()) {
                System.out.println("###Police caught you!###");
                break;
            }
        }
        showMap();
    }


    public static void Name() {
        System.out.print("Enter rider name: > ");
        riderName = sc.nextLine();
        System.out.println(riderName + " ready to start!");

    }

    public static void createMap(){
        map = new char[mapHeight][mapWidth];

        for (int y = 0; y < mapHeight; y++){
            for (int x = 0; x < mapWidth;x++){
                map[y][x] = emptyCell;
            }
        }
        System.out.println("Create map [" + mapWidth + "x" + mapHeight + "]");
    }

    public static void showMap() {
        System.out.println("=== MAP ===");
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                System.out.print(map[y][x] + "|");
            }
            System.out.println();
        }
        System.out.println("===========");
    }

    public static void spawnRider() {
        riderPosX = (2);
        riderPosY = (0);
        map[riderPosY][riderPosX] = rider;
        System.out.println(riderName + " has spawn in [" + riderPosY + ":" + riderPosX + "]");
    }

    public static void spawnPoliceCar() {

        policeCarCount = (mapWidth + mapHeight) / 2;

        int policeCarPosX;
        int policeCarPosY;

        for (int i = 1; i <= policeCarCount; i++) {

            do {
                policeCarPosX = random.nextInt(mapWidth);
                policeCarPosY = random.nextInt(mapHeight);
            } while (!isCellClear(policeCarPosX, policeCarPosY));
            map[policeCarPosY][policeCarPosX] = policeCar;
        }
    }

    public static void spawnFinish() {

        finishCount = mapWidth;

        int finishPosX;
        int finishPosY;

        for (int i = 1; i <= finishCount; i++) {

            do {
                finishPosX = random.nextInt(mapWidth);
                finishPosY = mapHeight - 1;
            } while (!isCellClear(finishPosX, finishPosY));
            map[finishPosY][finishPosX] = finish;
        }
    }

    public static void spawnHeal(){
        healCount = (mapWidth + mapHeight) / 4;

        int healPosX;
        int healPosY;

        for (int i = 1; i <= healCount; i++) {

            do {
                healPosX = random.nextInt(mapWidth);
                healPosY = random.nextInt(mapHeight);
            } while (!isCellClear(healPosX, healPosY));
            map[healPosY][healPosX] = heal;
        }
    }
    public static void riderMoveAction() {
        int riderLastPositionX = riderPosX;
        int riderLastPositionY = riderPosY;

        int riderMoveCode;

        do {
            System.out.print("Enter destination: (DOWN-" + rideMoveDown +
                    "|LEFT-" + riderMoveLeft + "|RIGHT-" + riderMoveRight + ") > ");
            riderMoveCode = sc.nextInt();

            switch (riderMoveCode) {
                case rideMoveDown:
                    riderPosY += 1;
                    break;
                case riderMoveLeft:
                    riderPosX -= 1;
                    break;
                case riderMoveRight:
                    riderPosX += 1;
                    break;
            }

        } while (!isValidRiderMove(riderLastPositionX, riderLastPositionY, riderPosX, riderPosY));

        riderNextCellAction();

        map[riderPosY][riderPosX] = rider;
        map[riderLastPositionY][riderLastPositionX] = emptyCell;
    }

    public static boolean isValidRiderMove(int lastX, int lastY, int nextX, int nextY) {
        if (isCellValid(nextX, nextY)) {
            System.out.println(riderName + " has move to [" + nextY + ":" + nextX + "]");
            return true;
        } else {
            System.out.println(riderName + " move to [" + nextY + ":" + nextX + "] invalid");
            riderPosX = lastX;
            riderPosY = lastY;
            return false;
        }
    }

    public static void riderNextCellAction() {
        if (map[riderPosY][riderPosX] == policeCar) {
            riderHealth -= policeCarPower;
            policeCarCount--;
            System.out.println(riderName + " get damage " + policeCarPower + ". " + riderName + " HP= " + riderHealth);
        }
        if (map[riderPosY][riderPosX] == heal) {
            riderHealth += healNumber;
            System.out.println(riderName + " get heal " + healNumber + ". " + riderName + " HP= " + riderHealth);
        }
        if (map[riderPosY][riderPosX] == finish){
            System.out.println("###You are Winner!###");
        }
    }

    public static boolean isCellClear(int x, int y) {
        return map[y][x] == emptyCell;
    }

    public static boolean isCellValid(int x, int y) {
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    public static boolean isRiderAlive() {
        return riderHealth > 0;
    }
}

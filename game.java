import java.util.*;

class Player {
    private int hp;
    private int item1; // knife
    private int item2; // magnifying glass
    private int item3; // drink
    private int item4; // handcuff
    private int item5; // cigar
    private Map<Integer, Integer> store;

    public Player() {
        hp = 6;
        item1 = 0;
        item2 = 0;
        item3 = 0;
        item4 = 0;
        item5 = 0;
        store = new HashMap<>();
    }

    public void display() {
        System.out.println("Hp : " + hp);
        System.out.println("Knife count : " + store.getOrDefault(1, 0));
        System.out.println("Magnifying glass count : " + store.getOrDefault(2, 0));
        System.out.println("Drink count : " + store.getOrDefault(3, 0));
        System.out.println("Handcuff count : " + store.getOrDefault(4, 0));
        System.out.println("Cigar count : " + store.getOrDefault(5, 0));
    }

    public int currentHp() {
        return hp;
    }

    public void add(int pos) {
        store.put(pos, store.getOrDefault(pos, 0) + 1);
    }

    public boolean checkVal(int pos) {
        if (store.getOrDefault(pos, 0) > 0) {
            store.put(pos, store.get(pos) - 1);
            return true;
        }
        return false;
    }

    public void incHp(int value) {
        hp += value;
    }
}

public class game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("start");
        System.out.println("press any key");
        char anykey = scanner.next().charAt(0);

        while (true) {
            Player human = new Player();
            Player bot = new Player();

            System.out.println("Enter your name");
            String name = scanner.next();
            System.out.println("In this game there will be a gun");
            System.out.println("Let Gun carry n bullets");
            System.out.println("So these bullet can be either real or fake");
            System.out.println("First turn will be yours");
            System.out.println("Next turn will be of Bot");
            System.out.println("Both of you have 6-6 life");
            System.out.println("You can shoot either yourself or Bot");
            System.out.println("If you shoot yourself and the current bullet is fake, the turn of the other one will get skipped so you get another turn again");
            System.out.println("Or If you shoot yourself and the current bullet is real, you will lose 1 life");
            System.out.println("But if you shoot the other one and it contains a real bullet, the other one will lose 1 life");
            System.out.println("Both of you get 4 items also and each item is at max one time but both of you get these before reloading of gun");
            System.out.println("Item 1 : Knife");
            System.out.println("It increases damage of bullet 2X");
            System.out.println("Item 2 : Magnifying glass");
            System.out.println("You can see if the current bullet is real or fake");
            System.out.println("Item 3 : Drink");
            System.out.println("This ejects the current bullet");
            System.out.println("Item 4 : Handcuff");
            System.out.println("This skips your opponent's turn so you get turn twice");
            System.out.println("Item 5 : Cigar");
            System.out.println("It increases your hp by 1");

            int bulletCount = 0;
            int realBullet = 0;
            int fakeBullet = 0;
            int round = 1;
            System.out.println("\n\n\n");

            while (human.currentHp() > 0 && bot.currentHp() > 0) {
                if (bulletCount == 0) {
                    System.out.println("Round number : " + round);
                    round++;
                    Random rand = new Random();
                    bulletCount = rand.nextInt(8) + 1;
                    realBullet = rand.nextInt(bulletCount) + 1;
                    fakeBullet = bulletCount - realBullet;
                    System.out.println("Bullet count : " + bulletCount);
                    System.out.println("Real Bullet count : " + realBullet);
                    System.out.println("Fake bullet count : " + fakeBullet);
                    int addTime = 0;
                    while (addTime < 4) {
                        int currItem = rand.nextInt(5) + 1;
                        human.add(currItem);
                        addTime++;
                    }
                    addTime = 0;
                    while (addTime < 4) {
                        int currItem = rand.nextInt(5) + 1;
                        bot.add(currItem);
                        addTime++;
                    }
                }

                System.out.println("\n\n\n");
                Queue<Integer> q = new LinkedList<>();
                Random rand = new Random();
                for (int p = 0; p < bulletCount && realBullet > 0 && fakeBullet > 0; p++) {
                    int currBullet = rand.nextInt(2) + 1;
                    q.add(currBullet);
                    if (currBullet == 1) realBullet--;
                    else fakeBullet--;
                }

                bulletCount = 0;
                while (realBullet > 0) {
                    q.add(1);
                    realBullet--;
                }
                while (fakeBullet > 0) {
                    q.add(0);
                    fakeBullet--;
                }

                while (human.currentHp() > 0 && bot.currentHp() > 0 && !q.isEmpty()) {
                    int turn = 1;
                    int damage = 1;

                    while (turn > 0 && !q.isEmpty()) {
                        System.out.println("Your turn");
                        System.out.println("Your stats");
                        human.display();
                        System.out.println("Bot stats");
                        bot.display();
                        System.out.println("Choose option");
                        System.out.println("1. to attack the other one");
                        System.out.println("2. to attack yourself");
                        System.out.println("3. to use knife");
                        System.out.println("4. to use magnifying glass");
                        System.out.println("5. to use drink");
                        System.out.println("6. to use handcuff");
                        System.out.println("7. to use cigar");
                        int option = scanner.nextInt();
                        System.out.println("Chosen option is : " + option);

                        if (option == 1 || option == 2) {
                            turn--;
                            System.out.println("Current bullet is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                            if (option == 1) {
                                if (q.peek() == 1) {
                                    System.out.println("Shoot");
                                    bot.incHp(-damage);
                                } else {
                                    System.out.println("Missed");
                                }
                            } else {
                                if (q.peek() == 1) {
                                    System.out.println("Ahh sh*t");
                                    human.incHp(-damage);
                                } else {
                                    System.out.println("ohh God!!");
                                }
                            }
                            q.poll();
                            damage = 1;
                        } else {
                            if (option == 3 && human.checkVal(option - 2)) {
                                System.out.println("Now you can damage twice in one move");
                                damage = 2;
                            }
                            if (option == 4 && human.checkVal(option - 2)) {
                                System.out.println("Current bullet is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                            }
                            if (option == 5 && human.checkVal(option - 2)) {
                                System.out.println("Current bullet which is ejected is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                                q.poll();
                            }
                            if (option == 6 && human.checkVal(option - 2)) {
                                turn++;
                                System.out.println("Now you have two turns continuously");
                            }
                            if (option == 7 && human.checkVal(option - 2)) {
                                human.incHp(1);
                                System.out.println("Your hp increases by one");
                            }
                        }
                        System.out.println("\n\n");
                    }

                    turn = 1;
                    damage = 1;

                    while (turn > 0 && !q.isEmpty()) {
                        System.out.println("Bot turn");
                        System.out.println("Your stats");
                        human.display();
                        System.out.println("Bot stats");
                        bot.display();
                        System.out.println("Choose option");
                        System.out.println("1. to attack the other one");
                        System.out.println("2. to attack yourself");
                        System.out.println("3. to use knife");
                        System.out.println("4. to use magnifying glass");
                        System.out.println("5. to use drink");
                        System.out.println("6. to use handcuff");
                        System.out.println("7. to use cigar");
                        int option = rand.nextInt(7) + 1;
                        System.out.println("Chosen option is : " + option);

                        if (option == 1 || option == 2) {
                            turn--;
                            System.out.println("Current bullet is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                            if (option == 1) {
                                if (q.peek() == 1) {
                                    System.out.println("Shoot");
                                    human.incHp(-damage);
                                } else {
                                    System.out.println("Missed");
                                }
                            } else {
                                if (q.peek() == 1) {
                                    System.out.println("Ahh sh*t");
                                    bot.incHp(-damage);
                                } else {
                                    System.out.println("Safe!!");
                                }
                            }
                            q.poll();
                            damage = 1;
                        } else {
                            if (option == 3 && bot.checkVal(option - 2)) {
                                System.out.println("Now you can damage twice in one move");
                                damage = 2;
                            }
                            if (option == 4 && bot.checkVal(option - 2)) {
                                System.out.println("Current bullet is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                            }
                            if (option == 5 && bot.checkVal(option - 2)) {
                                System.out.println("Current bullet which is ejected is : " + (q.peek() == 1 ? "Real Bullet" : "Fake Bullet"));
                                q.poll();
                            }
                            if (option == 6 && bot.checkVal(option - 2)) {
                                turn++;
                                System.out.println("Now you have two turns continuously");
                            }
                            if (option == 7 && bot.checkVal(option - 2)) {
                                bot.incHp(1);
                                System.out.println("Your hp increases by one");
                            }
                        }
                        System.out.println("\n\n");
                    }
                }
            }

            System.out.println("\n\n\n");
            System.out.println("Winner is : " + (human.currentHp() > 0 ? name : "Bot"));

            System.out.println("Want to retry?");
            System.out.println("Click y");
            System.out.println("Click n");
            char retry = scanner.next().charAt(0);
            if (retry == 'n') {
                break;
            }
        }

        scanner.close();
    }
}

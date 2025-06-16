package buddy;

import java.util.*;

public class BuddySystem {
    private final int maxLevel;
    private final int minLevel;
    private final Map<Integer, List<Integer>> freeLists; // Nível -> blocos livres (endereços)
    private final Map<Integer, Integer> allocatedBlocks; // Endereço -> Nível

    public BuddySystem(int totalSize, int minBlockSize) {
        this.minLevel = (int) (Math.log(minBlockSize) / Math.log(2));
        this.maxLevel = (int) (Math.log(totalSize) / Math.log(2));
        this.freeLists = new HashMap<>();
        this.allocatedBlocks = new HashMap<>();

        for (int i = minLevel; i <= maxLevel; i++) {
            freeLists.put(i, new ArrayList<>());
        }

        // Começamos com um único bloco livre no maior nível
        freeLists.get(maxLevel).add(0);
    }

    public synchronized Integer allocate(int size) {
        int requiredLevel = calculateLevel(size);

        for (int level = requiredLevel; level <= maxLevel; level++) {
            if (!freeLists.get(level).isEmpty()) {
                int address = split(level, requiredLevel);
                allocatedBlocks.put(address, requiredLevel);
                return address;
            }
        }
        return null; // Falha na alocação
    }

    public synchronized void free(int address) {
        Integer level = allocatedBlocks.remove(address);
        if (level == null) return;

        merge(address, level);
    }

    private int split(int fromLevel, int toLevel) {
        int address = freeLists.get(fromLevel).remove(0);

        while (fromLevel > toLevel) {
            fromLevel--;
            int buddyAddress = address + (1 << fromLevel);
            freeLists.get(fromLevel).add(buddyAddress);
        }

        return address;
    }

    private void merge(int address, int level) {
        while (level < maxLevel) {
            int buddyAddress = getBuddyAddress(address, level);

            List<Integer> list = freeLists.get(level);
            if (list.remove((Integer) buddyAddress)) {
                // Fuse and go one level up
                address = Math.min(address, buddyAddress);
                level++;
            } else {
                break;
            }
        }
        freeLists.get(level).add(address);
    }

    private int getBuddyAddress(int address, int level) {
        return address ^ (1 << level);
    }

    private int calculateLevel(int size) {
        int level = minLevel;
        int blockSize = 1 << level;
        while (blockSize < size && level < maxLevel) {
            level++;
            blockSize <<= 1;
        }
        return level;
    }

    public void printStatus() {
        System.out.println("Estado atual da memória:");
        for (int i = minLevel; i <= maxLevel; i++) {
            List<Integer> blocks = freeLists.get(i);
            if (!blocks.isEmpty()) {
                System.out.println("Nível " + i + " (bloco de " + (1 << i) + "): " + blocks);
            }
        }
    }
}

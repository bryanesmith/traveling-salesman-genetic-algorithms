/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmtravelingsalesman;

import java.util.Random;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class SalesmanMap {

    private final int height;
    private final int width;
    private static final Random random = new Random();
    private final byte[][] map;
    private int salesmanCol = 0;
    private int salesmanRow = 0;
    private byte lastSite = -1;

    /**
     * 
     * @param height
     * @param width
     */
    private SalesmanMap(final int height, final int width) {
        this.height = height;
        this.width = width;
        map = new byte[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                map[row][col] = -1;
            }
        }
    }

    /**
     * 
     * @param numPlacesToVisit
     * @param height
     * @param width
     * @return
     */
    public static SalesmanMap generate(int numPlacesToVisit, int height, int width) {
        SalesmanMap newMap = new SalesmanMap(height, width);
        
        int salesmanCol = 0;
        int salesmanRow = 0;
        
        ADD_SITES:
        for (byte i = 0; i < numPlacesToVisit; i++) {
            int col = random.nextInt(width);
            int row = random.nextInt(height);
            try {
                // If already flagged, set back counter and get next
                if (newMap.map[row][col] != -1) {
                    i--;
                    continue ADD_SITES;
                }
                
                // Start saleman on 'a', or 0
                if (i == 0) {
                    salesmanCol = col;
                    salesmanRow = row;
                }

                newMap.map[row][col] = (byte)i;
            } catch (NullPointerException npe) {
                System.err.println("NullPointerException: col=" + col + "; row=" + row);
                throw npe;
            }
        }
        
        newMap.lastSite = (byte)(numPlacesToVisit-1);
        
        newMap.salesmanCol = salesmanCol;
        newMap.salesmanRow = salesmanRow;

        return newMap;
    }

    @Override()
    public String toString() {
        return toString(false);
    }
    
    public String toString(boolean showSalesman) {
        StringBuffer mapBuffer = new StringBuffer();

        for (int col = 0; col < this.height; col++) {
            for (int row = 0; row < this.width; row++) {
                byte val = map[col][row];
                if (val != -1) {
                    // If the salesman is currently on the site
                    if (salesmanCol == row && salesmanRow == col && showSalesman) {
                        mapBuffer.append("^");
                    } else {
                        mapBuffer.append(convertByteToChar(val));
                    }
                } else {
                    // If the salesman is currently on non-site
                    if (salesmanCol == row && salesmanRow == col && showSalesman) {
                        mapBuffer.append("^");
                    } else {
                        mapBuffer.append(".");
                    }
                }
            }
            mapBuffer.append("\n");
        }

        return mapBuffer.toString();
    }
    
    /**
     * 
     * @param i
     * @return
     */
    public static char convertByteToChar(int i) {
        return convertByteToChar((byte)i);
    }
    
    /**
     * 
     * @param val
     * @return
     */
    public static char convertByteToChar(byte val) {
//        return (char)(val+48);
        return (char)(val+97);
    }
    
    public double getDistance(int start, int dest) {
        return getDistance((byte)start, (byte)dest);
    }
    
    public double getDistance(byte start, byte dest) {
        
        int startCol = -1;
        int startRow = -1;
        
        int destCol = -1;
        int destRow = -1;
        
        FIND_SITES:
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == start) {
                    startRow = row;
                    startCol = col;
                    
                    // Short-cut: if find both, exit
                    if (startCol != -1 && destCol != -1) {
                        break FIND_SITES;
                    }
                } 
                if (map[row][col] == dest) {
                    
                    destRow = row;
                    destCol = col;
                    
                    // Short-cut: if find both, exit
                    if (startCol != -1 && destCol != -1) {
                        break FIND_SITES;
                    }
                }
            }
        }
        
        double num1 = Math.pow((double)(destCol-startCol),2);
        double num2 = Math.pow((double)(destRow-startRow),2);
        
        double distance = Math.sqrt(num1+num2);
        return distance;
    }

    public byte getLastSite() {
        return lastSite;
    }
}

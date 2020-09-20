import java.util.Arrays;
import java.util.Scanner;

public class RleProgram
{
    // HEX CHAR TRANSLATOR //
    public static String hexCharEncode(String digit)
    {
        // Declare variables //
        String encodedChar = "0";

        // Assigns the character with a corresponding short //
        switch (digit) {
            case "0":
                encodedChar = "0";
                break;
            case "1":
                encodedChar = "1";
                break;
            case "2":
                encodedChar = "2";
                break;
            case "3":
                encodedChar = "3";
                break;
            case "4":
                encodedChar = "4";
                break;
            case "5":
                encodedChar = "5";
                break;
            case "6":
                encodedChar = "6";
                break;
            case "7":
                encodedChar = "7";
                break;
            case "8":
                encodedChar = "8";
                break;
            case "9":
                encodedChar = "9";
                break;
            case "10": // From here down are letters
                encodedChar = "A";
                break;
            case "11":
                encodedChar = "B";
                break;
            case "12":
                encodedChar = "C";
                break;
            case "13":
                encodedChar = "D";
                break;
            case "14":
                encodedChar = "E";
                break;
            case "15":
                encodedChar = "F";
                break;
            default:
                break;
        }
        return encodedChar;
    }

    // METHOD #1 //
    // Translates data (RLE or raw) a hexadecimal string (without delimiters) //
    public static String toHexString (byte[] data)
    {
        // Declare variables //
        String hexString = "";
        int i;

        for (i = 0; i < data.length; i++) {
            hexString = hexString + " " + Integer.toString(data[i], 16);
        }
        hexString = hexString.toUpperCase();
        return hexString;
    }

    // METHOD #2 //
    // Returns the number of runs of data in an image data set //
    public static int countRuns(byte[] flatData)
    {
        // Declare variables //
        int i;
        int runCount = 0;

        for (i = 0; i < flatData.length; i++) {
            if (i == 0 && flatData[i] != flatData[i + 1]) {
                runCount++;
            }
            else if (i == flatData.length - 1 && flatData[i] != flatData[i - 1]) {
                runCount++;
            }
            else if (i < flatData.length && flatData[i] != flatData[i +1])
            {
                runCount++;
            }
        }
        return runCount;
    }

    // METHOD #3 //
    // Returns an encoding (in RLE) of the raw data passed in //
    public static byte[] encodeRle(byte[] flatData)
    {
        // Declare variables //
        int i;
        int j = 0;
        byte digit;
        byte runLength = 1;
        byte [] encodedRle = new byte[2 * countRuns(flatData)];

        // Loops through array, adding to the run length if digits repeat //
        for (i = 0; i < flatData.length; i++) {
            digit = flatData[i];
            while (i + 1 < flatData.length && flatData[i] == flatData[i + 1]) {
                runLength++;
            }
            if (i == flatData.length - 1 && flatData[i] == flatData[i - 1]) {
                runLength++;
            }

            encodedRle[j] = runLength; // first of 2-digit indicator
            encodedRle[j + 1] = digit; // second of 2-digit indicator

            // resets values to original for next 2-digit pair //
            runLength = 1;
            j += 2;
        }
        return encodedRle;
    }

    // METHOD #4 //
    // Returns the total size of a set of data in bytes once the given RLE data is decompressed //
    public static int getDecodedLength(byte[] rleData)
    {
        // Declare variables //
        int decodedLength;

        // result of method #2, doubled, gives the length of the encoded (RLE) byte array //
        decodedLength = countRuns(rleData) * 2;
        return decodedLength;
    }

    // METHOD #5 //
    // Returns the decoded data set from RLE encoded data //
    public static byte[] decodeRle(byte[] rleData)
    {
        // Declare variables //
        int i = 0;
        int j = 0;
        int totLength = 0;
        byte digit = 0;
        byte runLength = 1;

        // Loops through array to figure out length of new array //
        for (i = 0; i + 1 < rleData.length; i++) {
            if (i % 2 == 0) {
                runLength = rleData[i];
                totLength += runLength;
            }
        }
        byte [] decodedRle = new byte[totLength];
        runLength = 1;
        // Loops through array, adding to the run length if digits repeat //
        for (i = 0; i + 1 < rleData.length; i++) {
            if (i % 2 == 0)
            {
                runLength = rleData[i];
                digit = rleData[i + 1];
                int g = j + runLength;

                // Assigns new values to decodedRle //
                for (j=j; j < g; j++)
                {
                    if (j <= totLength)
                    {
                        decodedRle[j] = digit;
                    }
                }
            }
        }
        return decodedRle;
    }

    // METHOD #6 - might be wrong //
    // Translates a string in hexadecimal format into byte data (can be raw or RLE) //
    public static byte[] stringToData (String dataString)
    {
        // Declare all variables //
        byte[] byteData = new byte[dataString.length() / 2];
        int i;
        int index;
        int dataAsInteger;

        // Loops through dataString and converts to byte array //
        for (i = 0; i < dataString.length() / 2; i++) {
            index = i * 2;
            dataAsInteger = Integer.parseInt(dataString.substring(index, index + 2), 16);
            byteData[i] = (byte)dataAsInteger;
        }
        return  byteData;
    }

    // METHOD #7 - edit!//
    /* TranslatesRLE data into a human-readable representation. For each run,
       in  order, it should display the run length in decimal(1-2 digits);
       the run valuein hexadecimal(1 digit); and a delimiter, ‘:’, between runs. */
    public static String toRleString (byte[] rleData)
    {
        int runLength;
        int runValue;
        String rleString = "";

        return rleString;
    }

    // METHOD #8 //
    // Translates a string in human-readable RLE format (with delimiters) into RLE byte data //
    // Test input: 28:10:6b:10:10b:10:2b:10:12b:10:2b:10:5b:20:11b:10:6b:10 //
    public static byte[] stringToRle (String rleString)
    {
        // Declare variables //
        int i = 0;
        int j = 0;
        int g = 0;
        int intRunLength;
        int totLength = 0;
        byte digit = 0;
        byte runLength = 1;

        // Loops through array to figure out length of new array //
        for (i = 0; i < rleString.length(); i++) {
            if (i + 2 < rleString.length()
                    && Character.toString(rleString.charAt(i + 2)).equals(":")) // If 1 digit runlength
            {
                runLength = (byte) rleString.charAt(i);
                totLength += runLength;
            }
            else if (i + 3 < rleString.length() && !Character.toString(rleString.charAt(i)).equals(":")
                    && Character.toString(rleString.charAt(i + 3)).equals(":")) // If 2 digit runlength
            {
                intRunLength = (rleString.charAt(i) * 10) + (rleString.charAt(i + 1));
                runLength = (byte) intRunLength;
                totLength += runLength;
            }
            else if (rleString.indexOf(i) == rleString.length() - 2) // last digits: 2
            {
                runLength = (byte) rleString.charAt(i);
                totLength += runLength;
            }
            else if (i + 1 < rleString.length()
                    && rleString.indexOf(i) == rleString.length() - 3)// last digits: 3
            {
                intRunLength = (rleString.charAt(i) * 10) + (rleString.charAt(i + 1));
                runLength = (byte) intRunLength;
                totLength += runLength;
            }
        }
        byte [] rleArray = new byte[totLength];
        runLength = 1;

        // Loops through array, adding to the run length if digits repeat //
        for (i = 0; i < rleString.length(); i++)
        {
            if (i + 2 < rleString.length()
                    && Character.toString(rleString.charAt(i + 2)).equals(":")) // If 1 digit runlength
            {
                runLength = (byte) rleString.charAt(i);
                digit = (byte) rleString.charAt(i + 1);
                g = j + runLength;

                // Assigns new values to decodedRle //
                for (j=j; j < g; j++)
                {
                    if (j <= totLength)
                    {
                        rleArray[j] = digit;
                    }
                }
            }
            else if (i + 3 < rleString.length() && !Character.toString(rleString.charAt(i)).equals(":")
                    && Character.toString(rleString.charAt(i + 3)).equals(":")) // If 2 digit runlength
            {
                intRunLength = (rleString.charAt(i) * 10) + (rleString.charAt(i + 1));
                runLength = (byte) intRunLength;
                digit = (byte) rleString.charAt(i + 2);
                g = j + runLength;

                // Assigns new values to decodedRle //
                for (j = j; j < g; j++)
                {
                    if (j <= totLength)
                    {
                        rleArray[j] = digit;
                    }
                }
            }
            else if (i + 1 < rleString.length()
                    && rleString.indexOf(i) == rleString.length() - 2)// last digits: 2
            {
                runLength = (byte) rleString.charAt(i);
                digit = (byte) rleString.charAt(i + 1);
                g = j + runLength;

                // Assigns new values to decodedRle //
                for (j=j; j < g; j++)
                {
                    if (j <= totLength)
                    {
                        rleArray[j] = digit;
                    }
                }
            }
            else if (i + 2 < rleString.length() && !Character.toString(rleString.charAt(i)).equals(":")
                    && rleString.indexOf(i) == rleString.length() - 3) // last digits: 3
            {
                intRunLength = (rleString.charAt(i) * 10) + (rleString.charAt(i + 1));
                runLength = (byte) intRunLength;
                digit = (byte) rleString.charAt(i + 2);
                g = j + runLength;

                // Assigns new values to decodedRle //
                for (j = j; j < g; j++)
                {
                    if (j <= totLength)
                    {
                        rleArray[j] = digit;
                    }
                }
            }
        }
        return rleArray;
    }


    public static void main(String[] args)
    {
        // Declare all variables //
        Scanner scnr = new Scanner(System.in);
        int menuSelection;
        String fileName;
        String rleStringToDecode;
        String rleHexString;
        String flatHexString;


        // TEMPORARY //
        byte[] tempArray = stringToRle("28:10:6b:10:10b:10:2b:10:12b:10:2b:10:5b:20:11b:10:6b:10");
        String tempString = new String (tempArray);
        System.out.println(tempString);




        // Welcome statement //
        System.out.println("Welcome to the RLE image encoder!\n");
        System.out.println("Displaying Spectrum Image:");

        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);

        // Displays menu //
        System.out.println("RLE Menu");
        System.out.println("--------");
        System.out.println("0. Exit");
        System.out.println("1. Load File");
        System.out.println("2. Load Test Image");
        System.out.println("3. Read RLE String");
        System.out.println("4. Read RLE Hex String");
        System.out.println("5. Read Data Hex String");
        System.out.println("6. Display Image");
        System.out.println("7. Display RLE String");
        System.out.println("8. Display Hex RLE Data");
        System.out.println("9. Display Hex Flat Data\n");
        System.out.print("Select Menu Option: ");
        menuSelection = scnr.nextInt();

        // User selects menu option //
        switch (menuSelection) {
            case 0:
                System.exit(0);
                break;
            case 1: // Load file
                System.out.print("Enter name of file to load: ");
                fileName = scnr.next();
                ConsoleGfx.loadFile(fileName);
                break;
            case 2: // Load test image
                System.out.println("Test image data loaded.");
                break;
            case 3: // Read RLE string
                String decodedRleString;
                System.out.print("Enter an RLE string to be decoded: ");
                rleStringToDecode = scnr.next();
                break;
            case 4: // Read RLE hex string
                System.out.print("Enter the hex string holding RLE data: ");
                rleHexString = scnr.next();
                break;
            case 5: // Read data hex string
                System.out.print("Enter the hex string holding flat data: ");
                flatHexString = scnr.next();
                break;
            case 6: // Display image
                System.out.println("Displaying image...");
                break;
            case 7: // Display RLE string
                System.out.print("RLE representation: ");
                break;
            case 8: // Display hex data
                System.out.print("RLE hex values: ");
                break;
            case 9: // Display hex flat data
                System.out.print("Flat hex values: ");
                break;
            default: // Invalid input
                System.out.println("Error! Invalid input.");
        }
    }
}

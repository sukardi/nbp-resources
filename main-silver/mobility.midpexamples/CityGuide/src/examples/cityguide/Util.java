/*
 *
 * Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved.

 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle Corporation nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package examples.cityguide;

import javax.microedition.lcdui.Gauge;
import javax.microedition.location.AddressInfo;
import javax.microedition.location.Landmark;
import javax.microedition.location.LandmarkStore;
import javax.microedition.location.QualifiedCoordinates;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;


/**
 * general utilities for CityGuide
 */
public class Util {
    /**
     * This method reads landmarks from InputStream.
     * Landmarks are stored in text file as values with delimiter ","
     * Method updates progress bar.
     *
     * @param is            character stream containing records of landmarks
     * @param progressGauge if not null external progress indicator is updated.
     */
    public static void readLandmarksFromStream (
        LandmarkStore store, InputStream is,
        Gauge progressGauge) throws IOException {
        int count = 0;
        int nchars;
        char[] buffer = new char[10]; //slowdown reading
        InputStreamReader isr = new InputStreamReader (is);
        StringBuffer record = new StringBuffer ();

        do {
            nchars = isr.read (buffer);

            for (int i = 0; i < nchars; i++) {
                if (buffer[i] == '\n') {
                    addLandmarkFromString (store, record.toString ());

                    if (progressGauge != null) {
                        progressGauge.setValue (count++);
                    }

                    record = new StringBuffer ();
                }
                else {
                    record.append (buffer[i]);
                }
            }
        }
        while (nchars == buffer.length);

        addLandmarkFromString (store, record.toString ());
        progressGauge.setValue (count++);
    }

    /**
     * This method creates landmark from values in String record.
     * Values are separated by comma. Sequence of the values is firm.
     * Latitude, Longitude, Altitude,Name, Description, Street & house number,
     * City, Post Code, Phone Number, Category
     *
     * @param record comma separated values
     */
    public static void addLandmarkFromString (LandmarkStore store, String record)
        throws IOException {
        Landmark landmark;

        try {
            Enumeration e = parseSVRecord (record, ',');

            if (!e.hasMoreElements ()) {
                return;
            }

            double xCoord = Double.parseDouble ((String) e.nextElement ());
            double yCoord = Double.parseDouble ((String) e.nextElement ());
            float altitude = Float.parseFloat ((String) e.nextElement ());
            String name = (String) e.nextElement ();
            String description = (String) e.nextElement ();
            String street = (String) e.nextElement ();
            String pcode = (String) e.nextElement ();
            String phone = (String) e.nextElement ();
            String city = (String) e.nextElement ();
            String category = (String) e.nextElement ();

            //create coordinates
            QualifiedCoordinates coords = new QualifiedCoordinates (xCoord, yCoord, altitude, 0, 0);

            //create address
            AddressInfo address = new AddressInfo ();
            address.setField (AddressInfo.STREET, street);
            address.setField (AddressInfo.CITY, city);
            address.setField (AddressInfo.POSTAL_CODE, pcode);
            address.setField (AddressInfo.PHONE_NUMBER, phone);

            landmark = new Landmark (name, description, coords, address);

            Enumeration c = store.getCategories ();
            boolean exists;

            for (exists = false; c.hasMoreElements ();)
                if (category.equals (c.nextElement ())) {
                    exists = true;

                    break;
                }

            if (!exists) {
                store.addCategory (category);
            }

            store.addLandmark (landmark, category);
        }
        catch (NoSuchElementException e) {
            throw new IOException ("Incorrect number of fields in landmark record.");
        }
        catch (SecurityException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IOException ("Cannot add landmark.");
        }
    }

    /**
     * This method parses separated values from record.
     *
     * @param str       record containing separated values
     * @param delimiter values delimiter char
     * @return String values from record as Enumeration
     */
    public static Enumeration parseSVRecord (String str, char delimiter) {
        int length = str.length ();
        int beginindex = 0;
        Vector v = new Vector ();

        for (int i = 0; i < length; i++) {
            if (str.charAt (i) == delimiter) {
                v.addElement (str.substring (beginindex, i).trim ());
                beginindex = i + 1;
            }

            if (i == (length - 1)) {
                v.addElement (str.substring (beginindex, length).trim ());
            }
        }

        return v.elements ();
    }

    public static String[] getArrayOfExistingCategories(LandmarkStore landmarkStore) throws IOException{        
        Enumeration categories = landmarkStore.getCategories();
        Vector existingCategories = new Vector();
        
        for(; categories.hasMoreElements();){
            String category = (String)categories.nextElement();
            if(landmarkStore.getLandmarks(category, null) != null){
                existingCategories.addElement(category);
            }
        }
        
        String[] array = new String[existingCategories.size()];
        existingCategories.copyInto(array);
        return array;
    }
}

/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is "SMS Library for the Java platform".
 *
 * The Initial Developer of the Original Code is Markus Eriksson.
 * Portions created by the Initial Developer are Copyright (C) 2002, 2003, 2004
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package org.marre.xml.text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Stack;

import org.marre.xml.XmlAttribute;
import org.marre.xml.XmlWriter;

public class TextXmlWriter implements XmlWriter
{
    protected StringWriter myXmlDocument = null;
    protected Stack myTagStack = null;
    
    protected boolean myCharsAddedBetweenTags = true;
    
    public TextXmlWriter()
    {
	    reset();
    }
    
    public void setDoctype(String publicID)
    {
    }

    public void setDoctype(String name, String systemURI)
    {
    }

    public void setDoctype(String name, String publicID, String publicURI)
    {
    }

    public void addStartElement(String tag) 
    	throws IOException
    {
        if (!myCharsAddedBetweenTags)
        {
            myXmlDocument.write("\r\n");
        }
        myCharsAddedBetweenTags = false;
        
        myXmlDocument.write("<" + tag + ">");
        myTagStack.push(tag);
    }

    public void addStartElement(String tag, XmlAttribute[] attribs)
        throws IOException
    {
        throw new IOException("Not implemented");
    }

    public void addEmptyElement(String tag) 
    	throws IOException
    {
        if (!myCharsAddedBetweenTags)
        {
            myXmlDocument.write("\r\n");
        }
        myCharsAddedBetweenTags = false;
        
        myXmlDocument.write("<" + tag + "/>\r\n");
    }

    public void addEmptyElement(String tag, XmlAttribute[] attribs)
        throws IOException
    {
        throw new IOException("Not implemented");
    }

    public void addEndTag() 
    	throws IOException
    {
        String tag = (String)myTagStack.pop();
        myXmlDocument.write("</" + tag + ">\r\n");
    }

    public void addCharacters(char[] ch, int start, int length)
        throws IOException
    {
        myCharsAddedBetweenTags = true;
        myXmlDocument.write(ch, start, length);
    }

    public void addCharacters(String str)
    	throws IOException
    {
        myCharsAddedBetweenTags = true;
        myXmlDocument.write(str);
    }

    public void reset()
    {
        myXmlDocument = new StringWriter(1024);
        myTagStack = new Stack();
        
        myCharsAddedBetweenTags = true;        
    }

    public void writeTo(OutputStream os) 	
    	throws IOException
    {
        //TODO: doctype
        String str = myXmlDocument.toString();
        os.write(str.getBytes());
    }
}
<?xml version='1.0' encoding='ISO-8859-1' ?>
<!--
*     Copyright (c) 2010 Oracle and/or its affiliates. All rights reserved.

Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.
*     Use is subject to license terms.
-->
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
         "http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="2.0">


  <!-- title -->
  <title>NetBeans Modules Help</title>

  <!-- maps -->
  <maps>
     <homeID>nbmodule_about</homeID>
     <mapref location="nbmodule-map.xml"/>
  </maps>

  <!-- views -->
  <view>
    <name>TOC</name>
    <label>Table Of Contents</label>
    <type>javax.help.TOCView</type>
    <data>nbmodule-toc.xml</data>
  </view>


  <view>
    <name>Search</name>
    <label>Search</label>
    <type>javax.help.SearchView</type>
    <data engine="com.sun.java.help.search.DefaultSearchEngine">
      JavaHelpSearch
    </data>
  </view>

</helpset>

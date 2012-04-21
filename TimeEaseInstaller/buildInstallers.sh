#!/usr/bin/env bash

# This is the installer script that will create all needed installer information
# for Go-Time!.

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ] ; do SOURCE="$(readlink "$SOURCE")"; done

IZPACK_HOME="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
UPDATE_SITE=$IZPACK_HOME/../TimeEaseUpdateSite/
WIN_CONF_LOC=$IZPACK_HOME/windows
LIN_CONF_LOC=$IZPACK_HOME/linux
MAC_CONF_LOC=$IZPACK_HOME/mac
 
cd $IZPACK_HOME/bin
sh compile $LIN_CONF_LOC/Linux.gtk.x86_64-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $LIN_CONF_LOC/Linux.gtk.x86-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $WIN_CONF_LOC/Win32.x86_64-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $WIN_CONF_LOC/Win32.x86-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $MAC_CONF_LOC/Macosx.carbon.x86-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $MAC_CONF_LOC/Macosx.cocoa.x86_64-TimeEase-Installer.xml -b $UPDATE_SITE
sh compile $MAC_CONF_LOC/Macosx.cocoa.x86-TimeEase-Installer.xml -b $UPDATE_SITE

cd $IZPACK_HOME/utils/wrappers/izpack2app
python izpack2app.py $MAC_CONF_LOC/Macosx.carbon.x86-TimeEase-Installer.jar $MAC_CONF_LOC/Macosx.carbon.x86-TimeEase-Installer.app
python izpack2app.py $MAC_CONF_LOC/Macosx.cocoa.x86_64-TimeEase-Installer.jar $MAC_CONF_LOC/Macosx.cocoa.x86_64-TimeEase-Installer.app
python izpack2app.py $MAC_CONF_LOC/Macosx.cocoa.x86-TimeEase-Installer.jar $MAC_CONF_LOC/Macosx.cocoa.x86-TimeEase-Installer.app

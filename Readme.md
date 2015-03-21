RedisBungeeBridge
=================
**Expensive UUID look ups are not yet implemented, they should be soon!<br>This project is not affiliated with RedisBungee nor endorsed
 by the authors of RedisBungee**
<br>
This is a plugin that bridges RedisBungee plugins over to ProxyConn to allow easy and painless transition.
<br><br>
Due to the nature of the way RedisBungee functions vs ProxyConn it is not possible to fully translate all API methods. If a method 
is not translated it will throw a RuntimeException when called, this is so you can see what/if any changes need to be made.
<br><br>
Here is a list of methods that will not work, I suggest testing a **copy** of your proxy and checking for errors before deploying.
<br>
<ul>
    <li>sendChannelMessage</li>
    <li>getAllServers</li>
    <li>registerPubSubChannels</li>
    <li>unregisterPubSubChannels</li>
    <li>getLastOnline</li>
</ul>
**PLEASE NOTE: Not all features will work, please contact the developer of the (Or me) to check if the required features will work.**
<br><br>
**LICENSE:**<br>
Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
You must provide a link back to the original project and clearly point out any changes made to this project.
This license must be included in all project files.
Any changes merged with this project are property of the copyright holder but may include the author's name.

<br><br>
**DISCLAIMER:**<br>
This plugin is not endorsed or approved by the authors or RedisBungee. RedisBungee is licensed to its authors and assumes no responsibility
for the use of this plugin. Any problems with this plugin should be directed to its author and not to the authors of RedisBungee.
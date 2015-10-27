# PhpConsole
PHP console is eclipse plugin and PHP script for easier debugging and development PHP project with eclipse IDE.


#### Usage:
1. Install eclipse plugin from update site:
	http://semgen.info/PhpConsole/
	(In Eclipse: Help -> Install New Software... )

2. Download only one PHP script: phpconsole.php and put into your WEB project directory.
https://raw.githubusercontent.com/jankod/PhpConsole/master/phpconsole.php
3. In eclipse open plugin view: Window -> Show View -> Other -> PhpConsole
4. Enter URL of phpconsole.php script (for example: http://localhost/projectXYZ/phpconsole.php)
!["Sc1"](https://raw.githubusercontent.com/jankod/PhpConsole/master/sc2.PNG "Screenshot 1")

5. On your other php script include "phpconsole.php" script and call logDebug("message");
```php
include "phpconsole.php";
logDebug("some message");
```

Screenshot:
![Sc2](https://raw.githubusercontent.com/jankod/PhpConsole/master/sc1-text.PNG "Screenshot 2")


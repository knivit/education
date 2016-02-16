JSR 133 Java Memory Model
http://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html
http://dev.cheremin.info/2013/07/data-races-is-pure-evil.html

Tips:
1) To view compiled code,
   - download hsdis-amd64.dll
     http://netcologne.dl.sourceforge.net/project/fcml/fcml-1.1.1/hsdis-1.1.1-win32-amd64.zip
   - java -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:PrintAssemblyOptions=intel <class>

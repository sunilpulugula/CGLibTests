# CGLibTests

Stand alone program explains how to use CGLIB in creating proxy of any java class.


 CGLIB  is a bytecode generation framework, ofcourse there are many such frameworks which do same job.But CGLIB is high-level framework that would  dynamically change classes providing its proxy and substituting the functionality of some methods.
 
 Even JDK proxies can do same job,but there are some cons in JDK proxies,If a class implemented from Interface then(only) it can be proxified.Where as CGLIB does not have such restriction.It will create subclass(proxy) to the existing class.
 
 There are other frameworks like javasist,byte buddy which do same job but have its own pros & cons.I will come up with some other project with these frameworks.
 

package at.jku.cis.iVolunteer.marketplace.rule.engine.test;

public class RuleEngineTestData {
	
	public final String ruleFibonacci =  "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" +
	     	"import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Fibonacci;\r\n" + 
			"dialect \"mvel\"\r\n" + 
			"\r\n" + 
			"rule Recurse\r\n" + 
			"    salience 10\r\n" + 
			"    when\r\n" + 
			"        f : Fibonacci ( value == -1 )\r\n" + 
			"        not ( Fibonacci ( sequence == 1 ) )    \r\n" + 
			"    then\r\n" + 
			"        insert( new Fibonacci( f.sequence - 1 ) );\r\n" + 
			"        System.out.println( \"recurse for \" + f.sequence );\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule Bootstrap\r\n" + 
			"    when\r\n" + 
			"        f : Fibonacci( sequence == 1 || == 2, value == -1 ) // this is a multi-restriction || on a single field\r\n" + 
			"    then \r\n" + 
			"        modify ( f ){ value = 1 };\r\n" + 
			"        System.out.println( f.sequence + \" == \" + f.value );\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule Calculate\r\n" + 
			"    when\r\n" + 
			"        f1 : Fibonacci( s1 : sequence, value != -1 ) // here we bind sequence\r\n" + 
			"        f2 : Fibonacci( sequence == (s1 + 1 ), value != -1 ) // here we don't, just to demonstrate the different way bindings can be used\r\n" + 
			"        f3 : Fibonacci( s3 : sequence == (f2.sequence + 1 ), value == -1 )              \r\n" + 
			"    then    \r\n" + 
			"        modify ( f3 ) { value = f1.value + f2.value };\r\n" + 
			"        System.out.println( s3 + \" == \" + f3.value ); // see how you can access pattern and field  bindings\r\n" + 
			"end \r\n";
	
	public final String ruleHelloWorld = "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n" +
	        "import at.jku.cis.iVolunteer.marketplace.rule.engine.test.Message;\r\n" + 
			"global java.util.List list\r\n" + 
			" \r\n" + 
			"rule \"Hello World\"\r\n" + 
			"    dialect \"mvel\"\r\n" + 
			"    when\r\n" + 
			"        m : Message( status == Message.HELLO, message : message )\r\n" + 
			"    then\r\n" + 
			"        System.out.println( message );\r\n" + 
			"//        modify ( m ) { setMessage( \"Goodbyte cruel world\" ),\r\n" + 
			"//                       setStatus( Message.GOODBYE ) };\r\n" + 
			"    modify ( m ) { message = \"Goodbye cruel world\",\r\n" + 
			"                   status = Message.GOODBYE };\r\n" + 
			"end\r\n" + 
			"\r\n" + 
			"rule \"Good Bye\"\r\n" + 
			"    dialect \"java\"\r\n" + 
			"    when\r\n" + 
			"        Message( status == Message.GOODBYE, message : message )\r\n" + 
			"    then\r\n" + 
			"        System.out.println( message );\r\n" + 
			"end";

}

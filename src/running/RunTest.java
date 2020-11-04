package running;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RunTest {
	public static int score = 0;
	public static String result = "";
	public static String currentMethodName = null;
	ArrayList<String> methodsPassed = new ArrayList<String>();
	
	Run s1, s2;
	Run m1, m2;
	Run e1, e2;
	Run c1, c2;

	@BeforeEach
	public void setUp() throws Exception {
		currentMethodName = null;

		s1 = new Run(4, 18*60);
		s2 = new Run(5, 19*60);

		m1 = new Run(10, 53*60);
		m2 = new Run(11, 61*60);

		e1 = new Run(20, 94*60);
		e2 = new Run(21, 100*60);

		c1 = new Run(1, 1); //speed 3600
		c1.addToEnd(s1); //speed 13.33
		c1.addToEnd(s2); //speed 15.789
		c1.addToEnd(m1); //speed 11.32
		c1.addToEnd(m2); //10.819
		c1.addToEnd(e1); //12.765
		c1.addToEnd(e2); //12.6
		//c1 speeds: 
		//3600 -> 13.33 -> 15.789 -> 11.32 -> 10.819 -> 12.765 -> 12.6

		c2 = new Run(1, 2);
		for(int i=1; i < 1000; i++) {
			c2.addToEnd(new Run(i+1, 2*(i+1)));
		}
	}

	@Test @Order(3) @Graded(description="addToEnd", marks=10)
	public void testAddToEnd() {
		assertNull(s1.prev);
		assertNull(s1.next);

		s1.addToEnd(s2);		
		assertNull(s2.prev); //remember, we are adding an instance copy of s2 to end of s1
		assertNull(s2.next); 
		assertNotNull(s1.next);
		assertNotEquals(s1.next, s2); //remember, we are adding an instance copy of s2 to end of s1
		assertEquals(s2.distance, s1.next.distance);
		assertEquals(s2.time, s1.next.time);
		assertNull(s1.next.next);
		assertEquals(s1, s1.next.prev);
		assertNull(s1.next.prev.prev);

		s1.addToEnd(m1);
		assertNull(m1.prev);
		assertNull(m1.next);
		assertNotNull(s1.next.next);
		assertNull(s1.next.next.next);
		assertNotEquals(m1, s1.next.next);
		assertEquals(m1.distance, s1.next.next.distance);
		assertEquals(m1.time, s1.next.next.time);
		assertEquals(s1, s1.next.next.prev.prev);
		assertNull(s1.next.next.prev.prev.prev);

		s1.next.addToEnd(m2);
		assertNull(m2.prev);
		assertNull(m2.next);
		assertNotNull(s1.next.next.next);
		assertNull(s1.next.next.next.next);
		assertNotEquals(m2, s1.next.next.next);
		assertEquals(m2.distance, s1.next.next.next.distance);
		assertEquals(m2.time, s1.next.next.next.time);
		assertEquals(s1, s1.next.prev);
		assertEquals(s1, s1.next.next.next.prev.prev.prev);
		assertNull(s1.next.next.next.prev.prev.prev.prev);

		s1.addToEnd(e1);
		assertNull(e1.prev);
		assertNull(e1.next);
		assertNotNull(s1.next.next.next.next);
		assertNull(s1.next.next.next.next.next);
		assertNotEquals(e1, s1.next.next.next.next);
		assertEquals(e1.distance, s1.next.next.next.next.distance);
		assertEquals(e1.time, s1.next.next.next.next.time);
		assertEquals(s1, s1.next.next.next.next.prev.prev.prev.prev);
		assertNull(s1.next.next.next.next.prev.prev.prev.prev.prev);

		s1.next.next.addToEnd(e2);
		assertNull(e2.prev);
		assertNull(e2.next);
		assertNotNull(s1.next.next.next.next.next);
		assertNull(s1.next.next.next.next.next.next);
		assertNotEquals(e2, s1.next.next.next.next.next);
		assertEquals(e2.distance, s1.next.next.next.next.next.distance);
		assertEquals(e2.time, s1.next.next.next.next.next.time);
		assertEquals(s1, s1.next.next.next.next.next.prev.prev.prev.prev.prev);
		assertNull(s1.next.next.next.next.next.prev.prev.prev.prev.prev.prev);
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test @Order(4) @Graded(description="addToFront", marks=10)
	public void testAddToFront() {
		assertNull(s1.prev);
		assertNull(s1.next);

		s1.addToFront(s2);		
		assertNull(s2.prev); //remember, we are adding an instance copy of s2 before s1
		assertNull(s2.next); 
		assertNotNull(s1.prev);
		assertNull(s1.prev.prev);
		assertNotEquals(s1.prev, s2); //remember, we are adding an instance copy of s2 before s1
		assertEquals(s2.distance, s1.prev.distance);
		assertEquals(s2.time, s1.prev.time);
		assertNull(s1.prev.prev);
		assertEquals(s1, s1.prev.next);
		assertNull(s1.prev.next.next);

		s1.addToFront(m1);		
		assertNull(m1.prev);
		assertNull(m1.next); 
		assertNotNull(s1.prev.prev);
		assertNull(s1.prev.prev.prev);
		assertNotEquals(s1.prev.prev, s2);
		assertEquals(m1.distance, s1.prev.prev.distance);
		assertEquals(m1.time, s1.prev.prev.time);
		assertNull(s1.prev.prev.prev);
		assertEquals(s1, s1.prev.prev.next.next);
		assertNull(s1.prev.prev.next.next.next);

		s1.prev.addToFront(m2);		
		assertNull(m2.prev);
		assertNull(m2.next); 
		assertNotNull(s1.prev.prev.prev);
		assertNull(s1.prev.prev.prev.prev);
		assertNotEquals(s1.prev.prev.prev, s2);
		assertEquals(m2.distance, s1.prev.prev.prev.distance);
		assertEquals(m2.time, s1.prev.prev.prev.time);
		assertNull(s1.prev.prev.prev.prev);
		assertEquals(s1, s1.prev.prev.prev.next.next.next);
		assertNull(s1.prev.prev.prev.next.next.next.next);

		s1.prev.addToFront(e1);		
		assertNull(e1.prev);
		assertNull(e1.next); 
		assertNotNull(s1.prev.prev.prev.prev);
		assertNull(s1.prev.prev.prev.prev.prev);
		assertNotEquals(s1.prev.prev.prev.prev, s2);
		assertEquals(e1.distance, s1.prev.prev.prev.prev.distance);
		assertEquals(e1.time, s1.prev.prev.prev.prev.time);
		assertNull(s1.prev.prev.prev.prev.prev);
		assertEquals(s1, s1.prev.prev.prev.prev.next.next.next.next);
		assertNull(s1.prev.prev.prev.prev.next.next.next.next.next);
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test @Order(5) @Graded(description="size", marks=10)
	public void testSize() throws Exception {
		testAddToEnd();
		setUp();
		assertEquals(1, s1.size());
		assertEquals(7, c1.next.next.size());
		assertEquals(1000, c2.next.next.next.size());
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test @Order(6) @Graded(description="getIndex", marks=10)
	public void testGetIndex() throws Exception {
		testAddToEnd();
		setUp();
		assertEquals(0, s1.getIndex());
		assertEquals(0, c1.getIndex());
		assertEquals(0, c2.getIndex());
		assertEquals(3, c1.next.next.next.getIndex());
		Run r = c2;
		int idx = (int)(Math.random() * 999) + 1;
		for(int i=0; i < idx; i++) {
			r = r.next;
		}
		assertEquals(idx, r.getIndex());

		r = c2;
		for(int i=0; i < 999; i++) {
			r = r.next;
		}
		assertEquals(999, r.getIndex());

		for(int i=0; i < 600; i++) {
			r = r.prev;
		}
		assertEquals(399, r.getIndex());
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test @Order(7) @Graded(description="get", marks=10)
	public void testGet() throws Exception {
		testAddToEnd();
		setUp();
		assertNotNull(s1.get(0));
		assertEquals(4, s1.get(0).distance);
		assertEquals(1080, s1.get(0).time);

		assertNotNull(c1.get(0));
		assertEquals(1, c1.get(0).distance);
		assertEquals(1, c1.get(0).time);

		assertNotNull(c1.next.next.next.get(0));
		assertEquals(1, c1.next.next.next.get(0).distance);
		assertEquals(1, c1.next.next.next.get(0).time);

		assertNotNull(c1.get(6));
		assertEquals(21, c1.get(6).distance);
		assertEquals(6000, c1.get(6).time);

		assertNull(c1.get(-1));
		assertNull(c1.get(7));

		assertNull(c2.get(-1));
		assertNull(c2.get(1000));

		assertNotNull(c2.next.next.next.next.next.next.next.next.next.get(25));
		assertEquals(26, c2.next.next.next.next.next.next.next.next.next.get(25).distance);
		assertEquals(52, c2.next.next.next.next.next.next.next.next.next.get(25).time);

		assertNotNull(c2.get(999));
		assertEquals(1000, c2.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.get(999).distance);
		assertEquals(2000, c2.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.get(999).time);

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test @Order(8) @Graded(description="toString", marks=10)
	public void testToString() throws Exception {
		testAddToEnd();
		setUp();
		assertEquals("4.0 in 1080", s1.toString());
		assertEquals("1.0 in 1->4.0 in 1080->5.0 in 1140->10.0 in 3180->11.0 in 3660->20.0 in 5640->21.0 in 6000", c1.toString());
		assertEquals("1.0 in 2->2.0 in 4->3.0 in 6->4.0 in 8->5.0 in 10->6.0 in 12->7.0 in 14->8.0 in 16->9.0 in 18->10.0 in 20->11.0 in 22->12.0 in 24->13.0 in 26->14.0 in 28->15.0 in 30->16.0 in 32->17.0 in 34->18.0 in 36->19.0 in 38->20.0 in 40->21.0 in 42->22.0 in 44->23.0 in 46->24.0 in 48->25.0 in 50->26.0 in 52->27.0 in 54->28.0 in 56->29.0 in 58->30.0 in 60->31.0 in 62->32.0 in 64->33.0 in 66->34.0 in 68->35.0 in 70->36.0 in 72->37.0 in 74->38.0 in 76->39.0 in 78->40.0 in 80->41.0 in 82->42.0 in 84->43.0 in 86->44.0 in 88->45.0 in 90->46.0 in 92->47.0 in 94->48.0 in 96->49.0 in 98->50.0 in 100->51.0 in 102->52.0 in 104->53.0 in 106->54.0 in 108->55.0 in 110->56.0 in 112->57.0 in 114->58.0 in 116->59.0 in 118->60.0 in 120->61.0 in 122->62.0 in 124->63.0 in 126->64.0 in 128->65.0 in 130->66.0 in 132->67.0 in 134->68.0 in 136->69.0 in 138->70.0 in 140->71.0 in 142->72.0 in 144->73.0 in 146->74.0 in 148->75.0 in 150->76.0 in 152->77.0 in 154->78.0 in 156->79.0 in 158->80.0 in 160->81.0 in 162->82.0 in 164->83.0 in 166->84.0 in 168->85.0 in 170->86.0 in 172->87.0 in 174->88.0 in 176->89.0 in 178->90.0 in 180->91.0 in 182->92.0 in 184->93.0 in 186->94.0 in 188->95.0 in 190->96.0 in 192->97.0 in 194->98.0 in 196->99.0 in 198->100.0 in 200->101.0 in 202->102.0 in 204->103.0 in 206->104.0 in 208->105.0 in 210->106.0 in 212->107.0 in 214->108.0 in 216->109.0 in 218->110.0 in 220->111.0 in 222->112.0 in 224->113.0 in 226->114.0 in 228->115.0 in 230->116.0 in 232->117.0 in 234->118.0 in 236->119.0 in 238->120.0 in 240->121.0 in 242->122.0 in 244->123.0 in 246->124.0 in 248->125.0 in 250->126.0 in 252->127.0 in 254->128.0 in 256->129.0 in 258->130.0 in 260->131.0 in 262->132.0 in 264->133.0 in 266->134.0 in 268->135.0 in 270->136.0 in 272->137.0 in 274->138.0 in 276->139.0 in 278->140.0 in 280->141.0 in 282->142.0 in 284->143.0 in 286->144.0 in 288->145.0 in 290->146.0 in 292->147.0 in 294->148.0 in 296->149.0 in 298->150.0 in 300->151.0 in 302->152.0 in 304->153.0 in 306->154.0 in 308->155.0 in 310->156.0 in 312->157.0 in 314->158.0 in 316->159.0 in 318->160.0 in 320->161.0 in 322->162.0 in 324->163.0 in 326->164.0 in 328->165.0 in 330->166.0 in 332->167.0 in 334->168.0 in 336->169.0 in 338->170.0 in 340->171.0 in 342->172.0 in 344->173.0 in 346->174.0 in 348->175.0 in 350->176.0 in 352->177.0 in 354->178.0 in 356->179.0 in 358->180.0 in 360->181.0 in 362->182.0 in 364->183.0 in 366->184.0 in 368->185.0 in 370->186.0 in 372->187.0 in 374->188.0 in 376->189.0 in 378->190.0 in 380->191.0 in 382->192.0 in 384->193.0 in 386->194.0 in 388->195.0 in 390->196.0 in 392->197.0 in 394->198.0 in 396->199.0 in 398->200.0 in 400->201.0 in 402->202.0 in 404->203.0 in 406->204.0 in 408->205.0 in 410->206.0 in 412->207.0 in 414->208.0 in 416->209.0 in 418->210.0 in 420->211.0 in 422->212.0 in 424->213.0 in 426->214.0 in 428->215.0 in 430->216.0 in 432->217.0 in 434->218.0 in 436->219.0 in 438->220.0 in 440->221.0 in 442->222.0 in 444->223.0 in 446->224.0 in 448->225.0 in 450->226.0 in 452->227.0 in 454->228.0 in 456->229.0 in 458->230.0 in 460->231.0 in 462->232.0 in 464->233.0 in 466->234.0 in 468->235.0 in 470->236.0 in 472->237.0 in 474->238.0 in 476->239.0 in 478->240.0 in 480->241.0 in 482->242.0 in 484->243.0 in 486->244.0 in 488->245.0 in 490->246.0 in 492->247.0 in 494->248.0 in 496->249.0 in 498->250.0 in 500->251.0 in 502->252.0 in 504->253.0 in 506->254.0 in 508->255.0 in 510->256.0 in 512->257.0 in 514->258.0 in 516->259.0 in 518->260.0 in 520->261.0 in 522->262.0 in 524->263.0 in 526->264.0 in 528->265.0 in 530->266.0 in 532->267.0 in 534->268.0 in 536->269.0 in 538->270.0 in 540->271.0 in 542->272.0 in 544->273.0 in 546->274.0 in 548->275.0 in 550->276.0 in 552->277.0 in 554->278.0 in 556->279.0 in 558->280.0 in 560->281.0 in 562->282.0 in 564->283.0 in 566->284.0 in 568->285.0 in 570->286.0 in 572->287.0 in 574->288.0 in 576->289.0 in 578->290.0 in 580->291.0 in 582->292.0 in 584->293.0 in 586->294.0 in 588->295.0 in 590->296.0 in 592->297.0 in 594->298.0 in 596->299.0 in 598->300.0 in 600->301.0 in 602->302.0 in 604->303.0 in 606->304.0 in 608->305.0 in 610->306.0 in 612->307.0 in 614->308.0 in 616->309.0 in 618->310.0 in 620->311.0 in 622->312.0 in 624->313.0 in 626->314.0 in 628->315.0 in 630->316.0 in 632->317.0 in 634->318.0 in 636->319.0 in 638->320.0 in 640->321.0 in 642->322.0 in 644->323.0 in 646->324.0 in 648->325.0 in 650->326.0 in 652->327.0 in 654->328.0 in 656->329.0 in 658->330.0 in 660->331.0 in 662->332.0 in 664->333.0 in 666->334.0 in 668->335.0 in 670->336.0 in 672->337.0 in 674->338.0 in 676->339.0 in 678->340.0 in 680->341.0 in 682->342.0 in 684->343.0 in 686->344.0 in 688->345.0 in 690->346.0 in 692->347.0 in 694->348.0 in 696->349.0 in 698->350.0 in 700->351.0 in 702->352.0 in 704->353.0 in 706->354.0 in 708->355.0 in 710->356.0 in 712->357.0 in 714->358.0 in 716->359.0 in 718->360.0 in 720->361.0 in 722->362.0 in 724->363.0 in 726->364.0 in 728->365.0 in 730->366.0 in 732->367.0 in 734->368.0 in 736->369.0 in 738->370.0 in 740->371.0 in 742->372.0 in 744->373.0 in 746->374.0 in 748->375.0 in 750->376.0 in 752->377.0 in 754->378.0 in 756->379.0 in 758->380.0 in 760->381.0 in 762->382.0 in 764->383.0 in 766->384.0 in 768->385.0 in 770->386.0 in 772->387.0 in 774->388.0 in 776->389.0 in 778->390.0 in 780->391.0 in 782->392.0 in 784->393.0 in 786->394.0 in 788->395.0 in 790->396.0 in 792->397.0 in 794->398.0 in 796->399.0 in 798->400.0 in 800->401.0 in 802->402.0 in 804->403.0 in 806->404.0 in 808->405.0 in 810->406.0 in 812->407.0 in 814->408.0 in 816->409.0 in 818->410.0 in 820->411.0 in 822->412.0 in 824->413.0 in 826->414.0 in 828->415.0 in 830->416.0 in 832->417.0 in 834->418.0 in 836->419.0 in 838->420.0 in 840->421.0 in 842->422.0 in 844->423.0 in 846->424.0 in 848->425.0 in 850->426.0 in 852->427.0 in 854->428.0 in 856->429.0 in 858->430.0 in 860->431.0 in 862->432.0 in 864->433.0 in 866->434.0 in 868->435.0 in 870->436.0 in 872->437.0 in 874->438.0 in 876->439.0 in 878->440.0 in 880->441.0 in 882->442.0 in 884->443.0 in 886->444.0 in 888->445.0 in 890->446.0 in 892->447.0 in 894->448.0 in 896->449.0 in 898->450.0 in 900->451.0 in 902->452.0 in 904->453.0 in 906->454.0 in 908->455.0 in 910->456.0 in 912->457.0 in 914->458.0 in 916->459.0 in 918->460.0 in 920->461.0 in 922->462.0 in 924->463.0 in 926->464.0 in 928->465.0 in 930->466.0 in 932->467.0 in 934->468.0 in 936->469.0 in 938->470.0 in 940->471.0 in 942->472.0 in 944->473.0 in 946->474.0 in 948->475.0 in 950->476.0 in 952->477.0 in 954->478.0 in 956->479.0 in 958->480.0 in 960->481.0 in 962->482.0 in 964->483.0 in 966->484.0 in 968->485.0 in 970->486.0 in 972->487.0 in 974->488.0 in 976->489.0 in 978->490.0 in 980->491.0 in 982->492.0 in 984->493.0 in 986->494.0 in 988->495.0 in 990->496.0 in 992->497.0 in 994->498.0 in 996->499.0 in 998->500.0 in 1000->501.0 in 1002->502.0 in 1004->503.0 in 1006->504.0 in 1008->505.0 in 1010->506.0 in 1012->507.0 in 1014->508.0 in 1016->509.0 in 1018->510.0 in 1020->511.0 in 1022->512.0 in 1024->513.0 in 1026->514.0 in 1028->515.0 in 1030->516.0 in 1032->517.0 in 1034->518.0 in 1036->519.0 in 1038->520.0 in 1040->521.0 in 1042->522.0 in 1044->523.0 in 1046->524.0 in 1048->525.0 in 1050->526.0 in 1052->527.0 in 1054->528.0 in 1056->529.0 in 1058->530.0 in 1060->531.0 in 1062->532.0 in 1064->533.0 in 1066->534.0 in 1068->535.0 in 1070->536.0 in 1072->537.0 in 1074->538.0 in 1076->539.0 in 1078->540.0 in 1080->541.0 in 1082->542.0 in 1084->543.0 in 1086->544.0 in 1088->545.0 in 1090->546.0 in 1092->547.0 in 1094->548.0 in 1096->549.0 in 1098->550.0 in 1100->551.0 in 1102->552.0 in 1104->553.0 in 1106->554.0 in 1108->555.0 in 1110->556.0 in 1112->557.0 in 1114->558.0 in 1116->559.0 in 1118->560.0 in 1120->561.0 in 1122->562.0 in 1124->563.0 in 1126->564.0 in 1128->565.0 in 1130->566.0 in 1132->567.0 in 1134->568.0 in 1136->569.0 in 1138->570.0 in 1140->571.0 in 1142->572.0 in 1144->573.0 in 1146->574.0 in 1148->575.0 in 1150->576.0 in 1152->577.0 in 1154->578.0 in 1156->579.0 in 1158->580.0 in 1160->581.0 in 1162->582.0 in 1164->583.0 in 1166->584.0 in 1168->585.0 in 1170->586.0 in 1172->587.0 in 1174->588.0 in 1176->589.0 in 1178->590.0 in 1180->591.0 in 1182->592.0 in 1184->593.0 in 1186->594.0 in 1188->595.0 in 1190->596.0 in 1192->597.0 in 1194->598.0 in 1196->599.0 in 1198->600.0 in 1200->601.0 in 1202->602.0 in 1204->603.0 in 1206->604.0 in 1208->605.0 in 1210->606.0 in 1212->607.0 in 1214->608.0 in 1216->609.0 in 1218->610.0 in 1220->611.0 in 1222->612.0 in 1224->613.0 in 1226->614.0 in 1228->615.0 in 1230->616.0 in 1232->617.0 in 1234->618.0 in 1236->619.0 in 1238->620.0 in 1240->621.0 in 1242->622.0 in 1244->623.0 in 1246->624.0 in 1248->625.0 in 1250->626.0 in 1252->627.0 in 1254->628.0 in 1256->629.0 in 1258->630.0 in 1260->631.0 in 1262->632.0 in 1264->633.0 in 1266->634.0 in 1268->635.0 in 1270->636.0 in 1272->637.0 in 1274->638.0 in 1276->639.0 in 1278->640.0 in 1280->641.0 in 1282->642.0 in 1284->643.0 in 1286->644.0 in 1288->645.0 in 1290->646.0 in 1292->647.0 in 1294->648.0 in 1296->649.0 in 1298->650.0 in 1300->651.0 in 1302->652.0 in 1304->653.0 in 1306->654.0 in 1308->655.0 in 1310->656.0 in 1312->657.0 in 1314->658.0 in 1316->659.0 in 1318->660.0 in 1320->661.0 in 1322->662.0 in 1324->663.0 in 1326->664.0 in 1328->665.0 in 1330->666.0 in 1332->667.0 in 1334->668.0 in 1336->669.0 in 1338->670.0 in 1340->671.0 in 1342->672.0 in 1344->673.0 in 1346->674.0 in 1348->675.0 in 1350->676.0 in 1352->677.0 in 1354->678.0 in 1356->679.0 in 1358->680.0 in 1360->681.0 in 1362->682.0 in 1364->683.0 in 1366->684.0 in 1368->685.0 in 1370->686.0 in 1372->687.0 in 1374->688.0 in 1376->689.0 in 1378->690.0 in 1380->691.0 in 1382->692.0 in 1384->693.0 in 1386->694.0 in 1388->695.0 in 1390->696.0 in 1392->697.0 in 1394->698.0 in 1396->699.0 in 1398->700.0 in 1400->701.0 in 1402->702.0 in 1404->703.0 in 1406->704.0 in 1408->705.0 in 1410->706.0 in 1412->707.0 in 1414->708.0 in 1416->709.0 in 1418->710.0 in 1420->711.0 in 1422->712.0 in 1424->713.0 in 1426->714.0 in 1428->715.0 in 1430->716.0 in 1432->717.0 in 1434->718.0 in 1436->719.0 in 1438->720.0 in 1440->721.0 in 1442->722.0 in 1444->723.0 in 1446->724.0 in 1448->725.0 in 1450->726.0 in 1452->727.0 in 1454->728.0 in 1456->729.0 in 1458->730.0 in 1460->731.0 in 1462->732.0 in 1464->733.0 in 1466->734.0 in 1468->735.0 in 1470->736.0 in 1472->737.0 in 1474->738.0 in 1476->739.0 in 1478->740.0 in 1480->741.0 in 1482->742.0 in 1484->743.0 in 1486->744.0 in 1488->745.0 in 1490->746.0 in 1492->747.0 in 1494->748.0 in 1496->749.0 in 1498->750.0 in 1500->751.0 in 1502->752.0 in 1504->753.0 in 1506->754.0 in 1508->755.0 in 1510->756.0 in 1512->757.0 in 1514->758.0 in 1516->759.0 in 1518->760.0 in 1520->761.0 in 1522->762.0 in 1524->763.0 in 1526->764.0 in 1528->765.0 in 1530->766.0 in 1532->767.0 in 1534->768.0 in 1536->769.0 in 1538->770.0 in 1540->771.0 in 1542->772.0 in 1544->773.0 in 1546->774.0 in 1548->775.0 in 1550->776.0 in 1552->777.0 in 1554->778.0 in 1556->779.0 in 1558->780.0 in 1560->781.0 in 1562->782.0 in 1564->783.0 in 1566->784.0 in 1568->785.0 in 1570->786.0 in 1572->787.0 in 1574->788.0 in 1576->789.0 in 1578->790.0 in 1580->791.0 in 1582->792.0 in 1584->793.0 in 1586->794.0 in 1588->795.0 in 1590->796.0 in 1592->797.0 in 1594->798.0 in 1596->799.0 in 1598->800.0 in 1600->801.0 in 1602->802.0 in 1604->803.0 in 1606->804.0 in 1608->805.0 in 1610->806.0 in 1612->807.0 in 1614->808.0 in 1616->809.0 in 1618->810.0 in 1620->811.0 in 1622->812.0 in 1624->813.0 in 1626->814.0 in 1628->815.0 in 1630->816.0 in 1632->817.0 in 1634->818.0 in 1636->819.0 in 1638->820.0 in 1640->821.0 in 1642->822.0 in 1644->823.0 in 1646->824.0 in 1648->825.0 in 1650->826.0 in 1652->827.0 in 1654->828.0 in 1656->829.0 in 1658->830.0 in 1660->831.0 in 1662->832.0 in 1664->833.0 in 1666->834.0 in 1668->835.0 in 1670->836.0 in 1672->837.0 in 1674->838.0 in 1676->839.0 in 1678->840.0 in 1680->841.0 in 1682->842.0 in 1684->843.0 in 1686->844.0 in 1688->845.0 in 1690->846.0 in 1692->847.0 in 1694->848.0 in 1696->849.0 in 1698->850.0 in 1700->851.0 in 1702->852.0 in 1704->853.0 in 1706->854.0 in 1708->855.0 in 1710->856.0 in 1712->857.0 in 1714->858.0 in 1716->859.0 in 1718->860.0 in 1720->861.0 in 1722->862.0 in 1724->863.0 in 1726->864.0 in 1728->865.0 in 1730->866.0 in 1732->867.0 in 1734->868.0 in 1736->869.0 in 1738->870.0 in 1740->871.0 in 1742->872.0 in 1744->873.0 in 1746->874.0 in 1748->875.0 in 1750->876.0 in 1752->877.0 in 1754->878.0 in 1756->879.0 in 1758->880.0 in 1760->881.0 in 1762->882.0 in 1764->883.0 in 1766->884.0 in 1768->885.0 in 1770->886.0 in 1772->887.0 in 1774->888.0 in 1776->889.0 in 1778->890.0 in 1780->891.0 in 1782->892.0 in 1784->893.0 in 1786->894.0 in 1788->895.0 in 1790->896.0 in 1792->897.0 in 1794->898.0 in 1796->899.0 in 1798->900.0 in 1800->901.0 in 1802->902.0 in 1804->903.0 in 1806->904.0 in 1808->905.0 in 1810->906.0 in 1812->907.0 in 1814->908.0 in 1816->909.0 in 1818->910.0 in 1820->911.0 in 1822->912.0 in 1824->913.0 in 1826->914.0 in 1828->915.0 in 1830->916.0 in 1832->917.0 in 1834->918.0 in 1836->919.0 in 1838->920.0 in 1840->921.0 in 1842->922.0 in 1844->923.0 in 1846->924.0 in 1848->925.0 in 1850->926.0 in 1852->927.0 in 1854->928.0 in 1856->929.0 in 1858->930.0 in 1860->931.0 in 1862->932.0 in 1864->933.0 in 1866->934.0 in 1868->935.0 in 1870->936.0 in 1872->937.0 in 1874->938.0 in 1876->939.0 in 1878->940.0 in 1880->941.0 in 1882->942.0 in 1884->943.0 in 1886->944.0 in 1888->945.0 in 1890->946.0 in 1892->947.0 in 1894->948.0 in 1896->949.0 in 1898->950.0 in 1900->951.0 in 1902->952.0 in 1904->953.0 in 1906->954.0 in 1908->955.0 in 1910->956.0 in 1912->957.0 in 1914->958.0 in 1916->959.0 in 1918->960.0 in 1920->961.0 in 1922->962.0 in 1924->963.0 in 1926->964.0 in 1928->965.0 in 1930->966.0 in 1932->967.0 in 1934->968.0 in 1936->969.0 in 1938->970.0 in 1940->971.0 in 1942->972.0 in 1944->973.0 in 1946->974.0 in 1948->975.0 in 1950->976.0 in 1952->977.0 in 1954->978.0 in 1956->979.0 in 1958->980.0 in 1960->981.0 in 1962->982.0 in 1964->983.0 in 1966->984.0 in 1968->985.0 in 1970->986.0 in 1972->987.0 in 1974->988.0 in 1976->989.0 in 1978->990.0 in 1980->991.0 in 1982->992.0 in 1984->993.0 in 1986->994.0 in 1988->995.0 in 1990->996.0 in 1992->997.0 in 1994->998.0 in 1996->999.0 in 1998->1000.0 in 2000", c2.toString());
		assertEquals("1.0 in 2->2.0 in 4->3.0 in 6->4.0 in 8->5.0 in 10->6.0 in 12->7.0 in 14->8.0 in 16->9.0 in 18->10.0 in 20->11.0 in 22->12.0 in 24->13.0 in 26->14.0 in 28->15.0 in 30->16.0 in 32->17.0 in 34->18.0 in 36->19.0 in 38->20.0 in 40->21.0 in 42->22.0 in 44->23.0 in 46->24.0 in 48->25.0 in 50->26.0 in 52->27.0 in 54->28.0 in 56->29.0 in 58->30.0 in 60->31.0 in 62->32.0 in 64->33.0 in 66->34.0 in 68->35.0 in 70->36.0 in 72->37.0 in 74->38.0 in 76->39.0 in 78->40.0 in 80->41.0 in 82->42.0 in 84->43.0 in 86->44.0 in 88->45.0 in 90->46.0 in 92->47.0 in 94->48.0 in 96->49.0 in 98->50.0 in 100->51.0 in 102->52.0 in 104->53.0 in 106->54.0 in 108->55.0 in 110->56.0 in 112->57.0 in 114->58.0 in 116->59.0 in 118->60.0 in 120->61.0 in 122->62.0 in 124->63.0 in 126->64.0 in 128->65.0 in 130->66.0 in 132->67.0 in 134->68.0 in 136->69.0 in 138->70.0 in 140->71.0 in 142->72.0 in 144->73.0 in 146->74.0 in 148->75.0 in 150->76.0 in 152->77.0 in 154->78.0 in 156->79.0 in 158->80.0 in 160->81.0 in 162->82.0 in 164->83.0 in 166->84.0 in 168->85.0 in 170->86.0 in 172->87.0 in 174->88.0 in 176->89.0 in 178->90.0 in 180->91.0 in 182->92.0 in 184->93.0 in 186->94.0 in 188->95.0 in 190->96.0 in 192->97.0 in 194->98.0 in 196->99.0 in 198->100.0 in 200->101.0 in 202->102.0 in 204->103.0 in 206->104.0 in 208->105.0 in 210->106.0 in 212->107.0 in 214->108.0 in 216->109.0 in 218->110.0 in 220->111.0 in 222->112.0 in 224->113.0 in 226->114.0 in 228->115.0 in 230->116.0 in 232->117.0 in 234->118.0 in 236->119.0 in 238->120.0 in 240->121.0 in 242->122.0 in 244->123.0 in 246->124.0 in 248->125.0 in 250->126.0 in 252->127.0 in 254->128.0 in 256->129.0 in 258->130.0 in 260->131.0 in 262->132.0 in 264->133.0 in 266->134.0 in 268->135.0 in 270->136.0 in 272->137.0 in 274->138.0 in 276->139.0 in 278->140.0 in 280->141.0 in 282->142.0 in 284->143.0 in 286->144.0 in 288->145.0 in 290->146.0 in 292->147.0 in 294->148.0 in 296->149.0 in 298->150.0 in 300->151.0 in 302->152.0 in 304->153.0 in 306->154.0 in 308->155.0 in 310->156.0 in 312->157.0 in 314->158.0 in 316->159.0 in 318->160.0 in 320->161.0 in 322->162.0 in 324->163.0 in 326->164.0 in 328->165.0 in 330->166.0 in 332->167.0 in 334->168.0 in 336->169.0 in 338->170.0 in 340->171.0 in 342->172.0 in 344->173.0 in 346->174.0 in 348->175.0 in 350->176.0 in 352->177.0 in 354->178.0 in 356->179.0 in 358->180.0 in 360->181.0 in 362->182.0 in 364->183.0 in 366->184.0 in 368->185.0 in 370->186.0 in 372->187.0 in 374->188.0 in 376->189.0 in 378->190.0 in 380->191.0 in 382->192.0 in 384->193.0 in 386->194.0 in 388->195.0 in 390->196.0 in 392->197.0 in 394->198.0 in 396->199.0 in 398->200.0 in 400->201.0 in 402->202.0 in 404->203.0 in 406->204.0 in 408->205.0 in 410->206.0 in 412->207.0 in 414->208.0 in 416->209.0 in 418->210.0 in 420->211.0 in 422->212.0 in 424->213.0 in 426->214.0 in 428->215.0 in 430->216.0 in 432->217.0 in 434->218.0 in 436->219.0 in 438->220.0 in 440->221.0 in 442->222.0 in 444->223.0 in 446->224.0 in 448->225.0 in 450->226.0 in 452->227.0 in 454->228.0 in 456->229.0 in 458->230.0 in 460->231.0 in 462->232.0 in 464->233.0 in 466->234.0 in 468->235.0 in 470->236.0 in 472->237.0 in 474->238.0 in 476->239.0 in 478->240.0 in 480->241.0 in 482->242.0 in 484->243.0 in 486->244.0 in 488->245.0 in 490->246.0 in 492->247.0 in 494->248.0 in 496->249.0 in 498->250.0 in 500->251.0 in 502->252.0 in 504->253.0 in 506->254.0 in 508->255.0 in 510->256.0 in 512->257.0 in 514->258.0 in 516->259.0 in 518->260.0 in 520->261.0 in 522->262.0 in 524->263.0 in 526->264.0 in 528->265.0 in 530->266.0 in 532->267.0 in 534->268.0 in 536->269.0 in 538->270.0 in 540->271.0 in 542->272.0 in 544->273.0 in 546->274.0 in 548->275.0 in 550->276.0 in 552->277.0 in 554->278.0 in 556->279.0 in 558->280.0 in 560->281.0 in 562->282.0 in 564->283.0 in 566->284.0 in 568->285.0 in 570->286.0 in 572->287.0 in 574->288.0 in 576->289.0 in 578->290.0 in 580->291.0 in 582->292.0 in 584->293.0 in 586->294.0 in 588->295.0 in 590->296.0 in 592->297.0 in 594->298.0 in 596->299.0 in 598->300.0 in 600->301.0 in 602->302.0 in 604->303.0 in 606->304.0 in 608->305.0 in 610->306.0 in 612->307.0 in 614->308.0 in 616->309.0 in 618->310.0 in 620->311.0 in 622->312.0 in 624->313.0 in 626->314.0 in 628->315.0 in 630->316.0 in 632->317.0 in 634->318.0 in 636->319.0 in 638->320.0 in 640->321.0 in 642->322.0 in 644->323.0 in 646->324.0 in 648->325.0 in 650->326.0 in 652->327.0 in 654->328.0 in 656->329.0 in 658->330.0 in 660->331.0 in 662->332.0 in 664->333.0 in 666->334.0 in 668->335.0 in 670->336.0 in 672->337.0 in 674->338.0 in 676->339.0 in 678->340.0 in 680->341.0 in 682->342.0 in 684->343.0 in 686->344.0 in 688->345.0 in 690->346.0 in 692->347.0 in 694->348.0 in 696->349.0 in 698->350.0 in 700->351.0 in 702->352.0 in 704->353.0 in 706->354.0 in 708->355.0 in 710->356.0 in 712->357.0 in 714->358.0 in 716->359.0 in 718->360.0 in 720->361.0 in 722->362.0 in 724->363.0 in 726->364.0 in 728->365.0 in 730->366.0 in 732->367.0 in 734->368.0 in 736->369.0 in 738->370.0 in 740->371.0 in 742->372.0 in 744->373.0 in 746->374.0 in 748->375.0 in 750->376.0 in 752->377.0 in 754->378.0 in 756->379.0 in 758->380.0 in 760->381.0 in 762->382.0 in 764->383.0 in 766->384.0 in 768->385.0 in 770->386.0 in 772->387.0 in 774->388.0 in 776->389.0 in 778->390.0 in 780->391.0 in 782->392.0 in 784->393.0 in 786->394.0 in 788->395.0 in 790->396.0 in 792->397.0 in 794->398.0 in 796->399.0 in 798->400.0 in 800->401.0 in 802->402.0 in 804->403.0 in 806->404.0 in 808->405.0 in 810->406.0 in 812->407.0 in 814->408.0 in 816->409.0 in 818->410.0 in 820->411.0 in 822->412.0 in 824->413.0 in 826->414.0 in 828->415.0 in 830->416.0 in 832->417.0 in 834->418.0 in 836->419.0 in 838->420.0 in 840->421.0 in 842->422.0 in 844->423.0 in 846->424.0 in 848->425.0 in 850->426.0 in 852->427.0 in 854->428.0 in 856->429.0 in 858->430.0 in 860->431.0 in 862->432.0 in 864->433.0 in 866->434.0 in 868->435.0 in 870->436.0 in 872->437.0 in 874->438.0 in 876->439.0 in 878->440.0 in 880->441.0 in 882->442.0 in 884->443.0 in 886->444.0 in 888->445.0 in 890->446.0 in 892->447.0 in 894->448.0 in 896->449.0 in 898->450.0 in 900->451.0 in 902->452.0 in 904->453.0 in 906->454.0 in 908->455.0 in 910->456.0 in 912->457.0 in 914->458.0 in 916->459.0 in 918->460.0 in 920->461.0 in 922->462.0 in 924->463.0 in 926->464.0 in 928->465.0 in 930->466.0 in 932->467.0 in 934->468.0 in 936->469.0 in 938->470.0 in 940->471.0 in 942->472.0 in 944->473.0 in 946->474.0 in 948->475.0 in 950->476.0 in 952->477.0 in 954->478.0 in 956->479.0 in 958->480.0 in 960->481.0 in 962->482.0 in 964->483.0 in 966->484.0 in 968->485.0 in 970->486.0 in 972->487.0 in 974->488.0 in 976->489.0 in 978->490.0 in 980->491.0 in 982->492.0 in 984->493.0 in 986->494.0 in 988->495.0 in 990->496.0 in 992->497.0 in 994->498.0 in 996->499.0 in 998->500.0 in 1000->501.0 in 1002->502.0 in 1004->503.0 in 1006->504.0 in 1008->505.0 in 1010->506.0 in 1012->507.0 in 1014->508.0 in 1016->509.0 in 1018->510.0 in 1020->511.0 in 1022->512.0 in 1024->513.0 in 1026->514.0 in 1028->515.0 in 1030->516.0 in 1032->517.0 in 1034->518.0 in 1036->519.0 in 1038->520.0 in 1040->521.0 in 1042->522.0 in 1044->523.0 in 1046->524.0 in 1048->525.0 in 1050->526.0 in 1052->527.0 in 1054->528.0 in 1056->529.0 in 1058->530.0 in 1060->531.0 in 1062->532.0 in 1064->533.0 in 1066->534.0 in 1068->535.0 in 1070->536.0 in 1072->537.0 in 1074->538.0 in 1076->539.0 in 1078->540.0 in 1080->541.0 in 1082->542.0 in 1084->543.0 in 1086->544.0 in 1088->545.0 in 1090->546.0 in 1092->547.0 in 1094->548.0 in 1096->549.0 in 1098->550.0 in 1100->551.0 in 1102->552.0 in 1104->553.0 in 1106->554.0 in 1108->555.0 in 1110->556.0 in 1112->557.0 in 1114->558.0 in 1116->559.0 in 1118->560.0 in 1120->561.0 in 1122->562.0 in 1124->563.0 in 1126->564.0 in 1128->565.0 in 1130->566.0 in 1132->567.0 in 1134->568.0 in 1136->569.0 in 1138->570.0 in 1140->571.0 in 1142->572.0 in 1144->573.0 in 1146->574.0 in 1148->575.0 in 1150->576.0 in 1152->577.0 in 1154->578.0 in 1156->579.0 in 1158->580.0 in 1160->581.0 in 1162->582.0 in 1164->583.0 in 1166->584.0 in 1168->585.0 in 1170->586.0 in 1172->587.0 in 1174->588.0 in 1176->589.0 in 1178->590.0 in 1180->591.0 in 1182->592.0 in 1184->593.0 in 1186->594.0 in 1188->595.0 in 1190->596.0 in 1192->597.0 in 1194->598.0 in 1196->599.0 in 1198->600.0 in 1200->601.0 in 1202->602.0 in 1204->603.0 in 1206->604.0 in 1208->605.0 in 1210->606.0 in 1212->607.0 in 1214->608.0 in 1216->609.0 in 1218->610.0 in 1220->611.0 in 1222->612.0 in 1224->613.0 in 1226->614.0 in 1228->615.0 in 1230->616.0 in 1232->617.0 in 1234->618.0 in 1236->619.0 in 1238->620.0 in 1240->621.0 in 1242->622.0 in 1244->623.0 in 1246->624.0 in 1248->625.0 in 1250->626.0 in 1252->627.0 in 1254->628.0 in 1256->629.0 in 1258->630.0 in 1260->631.0 in 1262->632.0 in 1264->633.0 in 1266->634.0 in 1268->635.0 in 1270->636.0 in 1272->637.0 in 1274->638.0 in 1276->639.0 in 1278->640.0 in 1280->641.0 in 1282->642.0 in 1284->643.0 in 1286->644.0 in 1288->645.0 in 1290->646.0 in 1292->647.0 in 1294->648.0 in 1296->649.0 in 1298->650.0 in 1300->651.0 in 1302->652.0 in 1304->653.0 in 1306->654.0 in 1308->655.0 in 1310->656.0 in 1312->657.0 in 1314->658.0 in 1316->659.0 in 1318->660.0 in 1320->661.0 in 1322->662.0 in 1324->663.0 in 1326->664.0 in 1328->665.0 in 1330->666.0 in 1332->667.0 in 1334->668.0 in 1336->669.0 in 1338->670.0 in 1340->671.0 in 1342->672.0 in 1344->673.0 in 1346->674.0 in 1348->675.0 in 1350->676.0 in 1352->677.0 in 1354->678.0 in 1356->679.0 in 1358->680.0 in 1360->681.0 in 1362->682.0 in 1364->683.0 in 1366->684.0 in 1368->685.0 in 1370->686.0 in 1372->687.0 in 1374->688.0 in 1376->689.0 in 1378->690.0 in 1380->691.0 in 1382->692.0 in 1384->693.0 in 1386->694.0 in 1388->695.0 in 1390->696.0 in 1392->697.0 in 1394->698.0 in 1396->699.0 in 1398->700.0 in 1400->701.0 in 1402->702.0 in 1404->703.0 in 1406->704.0 in 1408->705.0 in 1410->706.0 in 1412->707.0 in 1414->708.0 in 1416->709.0 in 1418->710.0 in 1420->711.0 in 1422->712.0 in 1424->713.0 in 1426->714.0 in 1428->715.0 in 1430->716.0 in 1432->717.0 in 1434->718.0 in 1436->719.0 in 1438->720.0 in 1440->721.0 in 1442->722.0 in 1444->723.0 in 1446->724.0 in 1448->725.0 in 1450->726.0 in 1452->727.0 in 1454->728.0 in 1456->729.0 in 1458->730.0 in 1460->731.0 in 1462->732.0 in 1464->733.0 in 1466->734.0 in 1468->735.0 in 1470->736.0 in 1472->737.0 in 1474->738.0 in 1476->739.0 in 1478->740.0 in 1480->741.0 in 1482->742.0 in 1484->743.0 in 1486->744.0 in 1488->745.0 in 1490->746.0 in 1492->747.0 in 1494->748.0 in 1496->749.0 in 1498->750.0 in 1500->751.0 in 1502->752.0 in 1504->753.0 in 1506->754.0 in 1508->755.0 in 1510->756.0 in 1512->757.0 in 1514->758.0 in 1516->759.0 in 1518->760.0 in 1520->761.0 in 1522->762.0 in 1524->763.0 in 1526->764.0 in 1528->765.0 in 1530->766.0 in 1532->767.0 in 1534->768.0 in 1536->769.0 in 1538->770.0 in 1540->771.0 in 1542->772.0 in 1544->773.0 in 1546->774.0 in 1548->775.0 in 1550->776.0 in 1552->777.0 in 1554->778.0 in 1556->779.0 in 1558->780.0 in 1560->781.0 in 1562->782.0 in 1564->783.0 in 1566->784.0 in 1568->785.0 in 1570->786.0 in 1572->787.0 in 1574->788.0 in 1576->789.0 in 1578->790.0 in 1580->791.0 in 1582->792.0 in 1584->793.0 in 1586->794.0 in 1588->795.0 in 1590->796.0 in 1592->797.0 in 1594->798.0 in 1596->799.0 in 1598->800.0 in 1600->801.0 in 1602->802.0 in 1604->803.0 in 1606->804.0 in 1608->805.0 in 1610->806.0 in 1612->807.0 in 1614->808.0 in 1616->809.0 in 1618->810.0 in 1620->811.0 in 1622->812.0 in 1624->813.0 in 1626->814.0 in 1628->815.0 in 1630->816.0 in 1632->817.0 in 1634->818.0 in 1636->819.0 in 1638->820.0 in 1640->821.0 in 1642->822.0 in 1644->823.0 in 1646->824.0 in 1648->825.0 in 1650->826.0 in 1652->827.0 in 1654->828.0 in 1656->829.0 in 1658->830.0 in 1660->831.0 in 1662->832.0 in 1664->833.0 in 1666->834.0 in 1668->835.0 in 1670->836.0 in 1672->837.0 in 1674->838.0 in 1676->839.0 in 1678->840.0 in 1680->841.0 in 1682->842.0 in 1684->843.0 in 1686->844.0 in 1688->845.0 in 1690->846.0 in 1692->847.0 in 1694->848.0 in 1696->849.0 in 1698->850.0 in 1700->851.0 in 1702->852.0 in 1704->853.0 in 1706->854.0 in 1708->855.0 in 1710->856.0 in 1712->857.0 in 1714->858.0 in 1716->859.0 in 1718->860.0 in 1720->861.0 in 1722->862.0 in 1724->863.0 in 1726->864.0 in 1728->865.0 in 1730->866.0 in 1732->867.0 in 1734->868.0 in 1736->869.0 in 1738->870.0 in 1740->871.0 in 1742->872.0 in 1744->873.0 in 1746->874.0 in 1748->875.0 in 1750->876.0 in 1752->877.0 in 1754->878.0 in 1756->879.0 in 1758->880.0 in 1760->881.0 in 1762->882.0 in 1764->883.0 in 1766->884.0 in 1768->885.0 in 1770->886.0 in 1772->887.0 in 1774->888.0 in 1776->889.0 in 1778->890.0 in 1780->891.0 in 1782->892.0 in 1784->893.0 in 1786->894.0 in 1788->895.0 in 1790->896.0 in 1792->897.0 in 1794->898.0 in 1796->899.0 in 1798->900.0 in 1800->901.0 in 1802->902.0 in 1804->903.0 in 1806->904.0 in 1808->905.0 in 1810->906.0 in 1812->907.0 in 1814->908.0 in 1816->909.0 in 1818->910.0 in 1820->911.0 in 1822->912.0 in 1824->913.0 in 1826->914.0 in 1828->915.0 in 1830->916.0 in 1832->917.0 in 1834->918.0 in 1836->919.0 in 1838->920.0 in 1840->921.0 in 1842->922.0 in 1844->923.0 in 1846->924.0 in 1848->925.0 in 1850->926.0 in 1852->927.0 in 1854->928.0 in 1856->929.0 in 1858->930.0 in 1860->931.0 in 1862->932.0 in 1864->933.0 in 1866->934.0 in 1868->935.0 in 1870->936.0 in 1872->937.0 in 1874->938.0 in 1876->939.0 in 1878->940.0 in 1880->941.0 in 1882->942.0 in 1884->943.0 in 1886->944.0 in 1888->945.0 in 1890->946.0 in 1892->947.0 in 1894->948.0 in 1896->949.0 in 1898->950.0 in 1900->951.0 in 1902->952.0 in 1904->953.0 in 1906->954.0 in 1908->955.0 in 1910->956.0 in 1912->957.0 in 1914->958.0 in 1916->959.0 in 1918->960.0 in 1920->961.0 in 1922->962.0 in 1924->963.0 in 1926->964.0 in 1928->965.0 in 1930->966.0 in 1932->967.0 in 1934->968.0 in 1936->969.0 in 1938->970.0 in 1940->971.0 in 1942->972.0 in 1944->973.0 in 1946->974.0 in 1948->975.0 in 1950->976.0 in 1952->977.0 in 1954->978.0 in 1956->979.0 in 1958->980.0 in 1960->981.0 in 1962->982.0 in 1964->983.0 in 1966->984.0 in 1968->985.0 in 1970->986.0 in 1972->987.0 in 1974->988.0 in 1976->989.0 in 1978->990.0 in 1980->991.0 in 1982->992.0 in 1984->993.0 in 1986->994.0 in 1988->995.0 in 1990->996.0 in 1992->997.0 in 1994->998.0 in 1996->999.0 in 1998->1000.0 in 2000", c2.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.next.toString());
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	//tests for add, longestSequenceOver and getRunsOver to be written here

	@AfterEach
	public void logSuccess() throws NoSuchMethodException, SecurityException {
		if(currentMethodName != null && !methodsPassed.contains(currentMethodName)) {
			methodsPassed.add(currentMethodName);
			Method method = getClass().getMethod(currentMethodName);
			Graded graded = method.getAnnotation(Graded.class);
			score+=graded.marks();
			result+=graded.description()+" passed. Marks awarded: "+graded.marks()+"\n";
		}
	}
	
	@AfterAll
	public static void wrapUp() throws IOException {
		if(result.length() != 0) {
			result = result.substring(0, result.length()-1); //remove the last "\n"
		}
		System.out.println(result);
		System.out.println("Indicative mark: "+score);
		System.out.println();
	}
}

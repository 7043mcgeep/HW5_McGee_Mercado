
public class Levels {

	
	public static void setLevel1(Grid grid_1, Rock rocks[])
	{
		// Put in a ground level
		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-1);
				
		// Put in a ground level
		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-2);
				
		// Put in a ground level
		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-3);

		// Place blocks
		grid_1.setBlock(6,9);
		grid_1.setBlock(10,9);
		grid_1.setBlock(11,9);
		grid_1.setBlock(12,9);grid_1.setBlock(12,6);
		grid_1.setBlock(13,9);
		grid_1.setBlock(14,9);
		grid_1.setBlock(18,13);grid_1.setBlock(18,12);
		grid_1.setBlock(19,13);grid_1.setBlock(19,12);
		grid_1.setBlock(28,13);grid_1.setBlock(28,12);grid_1.setBlock(28,11);
		grid_1.setBlock(29,13);grid_1.setBlock(29,12);grid_1.setBlock(29,11);
		grid_1.setBlock(36,13);grid_1.setBlock(36,12);grid_1.setBlock(36,11);grid_1.setBlock(36,10);
		grid_1.setBlock(37,13);grid_1.setBlock(37,12);grid_1.setBlock(37,11);grid_1.setBlock(37,10);
		grid_1.setBlock(47,13);grid_1.setBlock(47,12);grid_1.setBlock(47,11);grid_1.setBlock(47,10);
		grid_1.setBlock(48,13);grid_1.setBlock(48,12);grid_1.setBlock(48,11);grid_1.setBlock(48,10);
		grid_1.setBlock(54,9);
		grid_1.setBlock(67,10);
		grid_1.setBlock(68,10);
		grid_1.setBlock(69,10);
		grid_1.setBlock(70,6);
		grid_1.setBlock(71,6);
		grid_1.setBlock(71,6);
		grid_1.setBlock(72,6);
		grid_1.setBlock(73,6);
		grid_1.setBlock(74,6);
		grid_1.setBlock(75,6);
		grid_1.setBlock(76,6);
		grid_1.setBlock(77,6);
		grid_1.setBlock(82,6);
		grid_1.setBlock(83,6);
		grid_1.setBlock(84,6);
		grid_1.setBlock(85,6);grid_1.setBlock(85,10);
		grid_1.setBlock(91,10);
		grid_1.setBlock(92,10);
		grid_1.setBlock(97,10);
		grid_1.setBlock(100,10);grid_1.setBlock(100,6);
		grid_1.setBlock(103,10);
		grid_1.setBlock(109,10);
		grid_1.setBlock(112,6);
		grid_1.setBlock(113,6);
		grid_1.setBlock(114,6);
		grid_1.setBlock(119,6);
		grid_1.setBlock(120,6);
		grid_1.setBlock(121,6);grid_1.setBlock(121,10);
		grid_1.setBlock(122,6);grid_1.setBlock(122,10);
		grid_1.setBlock(125,13);
		grid_1.setBlock(126,13);grid_1.setBlock(126,12);
		grid_1.setBlock(127,13);grid_1.setBlock(127,12);grid_1.setBlock(127,11);
		grid_1.setBlock(128,13);grid_1.setBlock(128,12);grid_1.setBlock(128,11);grid_1.setBlock(128,10);
		grid_1.setBlock(131,13);grid_1.setBlock(131,12);grid_1.setBlock(131,11);grid_1.setBlock(131,10);
		grid_1.setBlock(132,13);grid_1.setBlock(132,12);grid_1.setBlock(132,11);
		grid_1.setBlock(133,13);grid_1.setBlock(133,12);
		grid_1.setBlock(134,13);
		grid_1.setBlock(139,13);
		grid_1.setBlock(140,13);grid_1.setBlock(140,12);
		grid_1.setBlock(141,13);grid_1.setBlock(141,12);grid_1.setBlock(141,11);
		grid_1.setBlock(142,13);grid_1.setBlock(142,12);grid_1.setBlock(142,11);grid_1.setBlock(142,10);
		grid_1.setBlock(143,13);grid_1.setBlock(143,12);grid_1.setBlock(143,11);grid_1.setBlock(143,10);
		grid_1.setBlock(146,13);grid_1.setBlock(146,12);grid_1.setBlock(146,11);grid_1.setBlock(146,10);
		grid_1.setBlock(147,13);grid_1.setBlock(147,12);grid_1.setBlock(147,11);
		grid_1.setBlock(148,13);grid_1.setBlock(148,12);
		grid_1.setBlock(149,13);
		grid_1.setBlock(154,13);grid_1.setBlock(154,12);
		grid_1.setBlock(155,13);grid_1.setBlock(155,12);
		grid_1.setBlock(159,10);
		grid_1.setBlock(160,10);
		grid_1.setBlock(161,10);
		grid_1.setBlock(162,10);
		grid_1.setBlock(170,13);grid_1.setBlock(170,12);
		grid_1.setBlock(171,13);grid_1.setBlock(171,12);
		grid_1.setBlock(172,13);
		grid_1.setBlock(173,13);grid_1.setBlock(173,12);
		grid_1.setBlock(174,13);grid_1.setBlock(174,12);grid_1.setBlock(174,11);
		grid_1.setBlock(175,13);grid_1.setBlock(175,12);grid_1.setBlock(175,11);grid_1.setBlock(175,10);
		grid_1.setBlock(176,13);grid_1.setBlock(176,12);grid_1.setBlock(176,11);grid_1.setBlock(176,10);grid_1.setBlock(176,9);
		grid_1.setBlock(177,13);grid_1.setBlock(177,12);grid_1.setBlock(177,11);grid_1.setBlock(177,10);grid_1.setBlock(177,9);grid_1.setBlock(177,8);
		grid_1.setBlock(178,13);grid_1.setBlock(178,12);grid_1.setBlock(178,11);grid_1.setBlock(178,10);grid_1.setBlock(178,9);grid_1.setBlock(178,8);grid_1.setBlock(178,7);
		grid_1.setBlock(179,13);grid_1.setBlock(179,12);grid_1.setBlock(179,11);grid_1.setBlock(179,10);grid_1.setBlock(179,9);grid_1.setBlock(179,8);grid_1.setBlock(179,7);grid_1.setBlock(179,6);
		grid_1.setBlock(180,13);grid_1.setBlock(180,12);grid_1.setBlock(180,11);grid_1.setBlock(180,10);grid_1.setBlock(180,9);grid_1.setBlock(180,8);grid_1.setBlock(180,7);grid_1.setBlock(180,6);
		grid_1.setBlock(190,13);grid_1.setBlock(190,12);grid_1.setBlock(190,11);grid_1.setBlock(190,10);grid_1.setBlock(190,9);grid_1.setBlock(190,8);grid_1.setBlock(190,7);grid_1.setBlock(190,6);grid_1.setBlock(190,5);grid_1.setBlock(190,4);
		rocks[0] = new Rock(0,250,100,0);
		rocks[1] = new Rock(400,200,100,20);
		rocks[2] = new Rock(100,300,100,40);
		rocks[3] = new Rock(1000,200,120,30);
		rocks[4] = new Rock(1200,250,120,0);
		Main.portal = new Portal(grid_1, 7500, 499);
	}
	
	public static void setLevel2(Grid grid_1) {
		
		// Put in 3 pits
		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-1);

		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-2);

		for (int i = 0; i < Grid.MWIDTH; i++)
			if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
				grid_1.setBlock(i, Grid.MHEIGHT-3);
		
		
		grid_1.setBlock(12,10);
		grid_1.setBlock(13,10);
		grid_1.setBlock(14,10);
		grid_1.setBlock(15,10);
		grid_1.setBlock(16,10);
		
		grid_1.setBlock(19,13);
		
		grid_1.setBlock(21,13);grid_1.setBlock(21,12);
		
		grid_1.setBlock(23,13);grid_1.setBlock(23,12);grid_1.setBlock(23,11);
		
		grid_1.setBlock(25,13);grid_1.setBlock(25,12);grid_1.setBlock(25,11);grid_1.setBlock(25,10);
		
		grid_1.setBlock(27,13);grid_1.setBlock(27,12);grid_1.setBlock(27,11);grid_1.setBlock(27,10);
		
		grid_1.setBlock(29,13);grid_1.setBlock(29,12);grid_1.setBlock(29,11);
		
		grid_1.setBlock(31,9);
		
		grid_1.setBlock(33,13);grid_1.setBlock(33,12);grid_1.setBlock(33,11);
		
		grid_1.setBlock(35,13);grid_1.setBlock(35,12);
		
		grid_1.setBlock(41,10);grid_1.setBlock(41,9);grid_1.setBlock(41,8);
		grid_1.setBlock(42,10);
		grid_1.setBlock(43,10);grid_1.setBlock(43,9);grid_1.setBlock(43,8);
		grid_1.setBlock(44,8);
		grid_1.setBlock(45,8);
		grid_1.setBlock(46,10);grid_1.setBlock(46,9);grid_1.setBlock(46,8);
		grid_1.setBlock(47,10);
		grid_1.setBlock(48,10);grid_1.setBlock(48,9);grid_1.setBlock(48,8);
		
		grid_1.setBlock(54,10);grid_1.setBlock(54,9);grid_1.setBlock(54,8);grid_1.setBlock(54,7);grid_1.setBlock(54,6);
		grid_1.setBlock(55,8);grid_1.setBlock(55,7);grid_1.setBlock(55,6);
		
		grid_1.setBlock(56,12);grid_1.setBlock(56,11);grid_1.setBlock(56,10);grid_1.setBlock(56,5);grid_1.setBlock(56,4);
		grid_1.setBlock(57,12);grid_1.setBlock(57,11);grid_1.setBlock(57,10);grid_1.setBlock(57,5);grid_1.setBlock(57,4);
		
		grid_1.setBlock(60,10);grid_1.setBlock(60,5);grid_1.setBlock(60,4);
		grid_1.setBlock(61,10);grid_1.setBlock(61,5);grid_1.setBlock(61,4);
		grid_1.setBlock(62,10);grid_1.setBlock(62,5);grid_1.setBlock(62,4);
		grid_1.setBlock(63,10);grid_1.setBlock(63,5);grid_1.setBlock(63,4);
		grid_1.setBlock(64,10);grid_1.setBlock(64,9);grid_1.setBlock(64,8);grid_1.setBlock(64,7);grid_1.setBlock(64,6);grid_1.setBlock(64,5);grid_1.setBlock(64,4);
		grid_1.setBlock(65,10);grid_1.setBlock(65,9);grid_1.setBlock(65,8);grid_1.setBlock(65,7);grid_1.setBlock(65,6);grid_1.setBlock(65,5);grid_1.setBlock(65,4);
		
		grid_1.setBlock(68,5);grid_1.setBlock(68,4);
		grid_1.setBlock(69,5);grid_1.setBlock(69,4);
		
		grid_1.setBlock(70,10);grid_1.setBlock(70,9);grid_1.setBlock(70,8);grid_1.setBlock(70,7);grid_1.setBlock(70,6);grid_1.setBlock(70,5);grid_1.setBlock(70,4);
		
		grid_1.setBlock(71,10);grid_1.setBlock(71,5);grid_1.setBlock(71,4);
		grid_1.setBlock(72,10);grid_1.setBlock(72,5);grid_1.setBlock(72,4);
		
		grid_1.setBlock(75,10);grid_1.setBlock(75,9);grid_1.setBlock(75,8);grid_1.setBlock(75,7);grid_1.setBlock(75,6);
		grid_1.setBlock(76,10);grid_1.setBlock(76,9);grid_1.setBlock(76,8);grid_1.setBlock(76,7);grid_1.setBlock(76,6);
		
		grid_1.setBlock(79,10);grid_1.setBlock(79,5);grid_1.setBlock(79,4);
		grid_1.setBlock(80,10);grid_1.setBlock(80,5);grid_1.setBlock(80,4);
		grid_1.setBlock(81,10);grid_1.setBlock(81,5);grid_1.setBlock(81,4);
		grid_1.setBlock(82,10);grid_1.setBlock(82,5);grid_1.setBlock(82,4);
		
		grid_1.setBlock(87,9);grid_1.setBlock(87,8);
		grid_1.setBlock(88,9);grid_1.setBlock(88,8);
		grid_1.setBlock(89,9);grid_1.setBlock(89,8);
		grid_1.setBlock(90,9);grid_1.setBlock(90,8);
		grid_1.setBlock(91,9);grid_1.setBlock(91,8);
		grid_1.setBlock(92,9);grid_1.setBlock(92,8);
		
		grid_1.setBlock(108,13);grid_1.setBlock(108,12);grid_1.setBlock(108,11);
		grid_1.setBlock(109,13);grid_1.setBlock(109,12);grid_1.setBlock(109,11);
		
		grid_1.setBlock(115,13);grid_1.setBlock(115,12);grid_1.setBlock(115,11);grid_1.setBlock(115,10);
		grid_1.setBlock(116,13);grid_1.setBlock(116,12);grid_1.setBlock(116,11);grid_1.setBlock(116,10);
		
		grid_1.setBlock(121,13);grid_1.setBlock(121,12);
		grid_1.setBlock(122,13);grid_1.setBlock(122,12);
		
		grid_1.setBlock(128,13);grid_1.setBlock(128,12);grid_1.setBlock(128,11);
		grid_1.setBlock(129,13);grid_1.setBlock(129,12);grid_1.setBlock(129,11);
		
		grid_1.setBlock(139,13);
		grid_1.setBlock(140,13);grid_1.setBlock(140,12);
		grid_1.setBlock(141,13);grid_1.setBlock(141,12);grid_1.setBlock(141,11);
		grid_1.setBlock(142,13);grid_1.setBlock(142,12);grid_1.setBlock(142,11);grid_1.setBlock(142,10);
		grid_1.setBlock(143,13);grid_1.setBlock(143,12);grid_1.setBlock(143,11);grid_1.setBlock(143,10);
		
		grid_1.setBlock(145,12);grid_1.setBlock(145,7);
		grid_1.setBlock(146,12);grid_1.setBlock(146,7);
		grid_1.setBlock(147,12);grid_1.setBlock(147,7);
		grid_1.setBlock(148,12);grid_1.setBlock(148,7);
		
		grid_1.setBlock(152,9);
		grid_1.setBlock(153,9);
		grid_1.setBlock(154,9);
		grid_1.setBlock(155,9);
		grid_1.setBlock(156,9);
		grid_1.setBlock(157,9);
		
		grid_1.setBlock(162,12);grid_1.setBlock(162,7);
		grid_1.setBlock(163,12);grid_1.setBlock(163,7);
		grid_1.setBlock(164,12);grid_1.setBlock(164,7);
		grid_1.setBlock(165,12);grid_1.setBlock(165,7);
		
		grid_1.setBlock(170,13);
		grid_1.setBlock(171,13);grid_1.setBlock(171,12);
		grid_1.setBlock(172,13);grid_1.setBlock(172,12);grid_1.setBlock(172,11);
		grid_1.setBlock(173,13);grid_1.setBlock(173,12);grid_1.setBlock(173,11);grid_1.setBlock(173,10);
		grid_1.setBlock(174,13);grid_1.setBlock(174,12);grid_1.setBlock(174,11);grid_1.setBlock(174,10);grid_1.setBlock(174,9);
		grid_1.setBlock(175,13);grid_1.setBlock(175,12);grid_1.setBlock(175,11);grid_1.setBlock(175,10);grid_1.setBlock(175,9);grid_1.setBlock(175,8);
		grid_1.setBlock(176,13);grid_1.setBlock(176,12);grid_1.setBlock(176,11);grid_1.setBlock(176,10);grid_1.setBlock(176,9);grid_1.setBlock(176,8);grid_1.setBlock(176,7);
		grid_1.setBlock(177,13);grid_1.setBlock(177,12);grid_1.setBlock(177,11);grid_1.setBlock(177,10);grid_1.setBlock(177,9);grid_1.setBlock(177,8);grid_1.setBlock(177,7);grid_1.setBlock(177,6);
		grid_1.setBlock(178,13);grid_1.setBlock(178,12);grid_1.setBlock(178,11);grid_1.setBlock(178,10);grid_1.setBlock(178,9);grid_1.setBlock(178,8);grid_1.setBlock(178,7);grid_1.setBlock(178,6);
		grid_1.setBlock(179,13);grid_1.setBlock(179,12);grid_1.setBlock(179,11);grid_1.setBlock(179,10);grid_1.setBlock(179,9);grid_1.setBlock(179,8);grid_1.setBlock(179,7);grid_1.setBlock(179,6);grid_1.setBlock(179,5);grid_1.setBlock(179,4);
	
		Main.fuelcan = new Fuelcan(grid_1,9706,499);
	
	}
}

package com.innova.ticketsapp.utils;

public class ReturnValue {
	
	public static final int Device_Open_Err = -1;
	public static final int Serial_Open_Err = -1000;
	public static final int Packet_Send_Err = -1001;
	public static final int Packet_Recv_Err = -1002;
	public static final int App_Permissions_Denial = -1004;
	
//system
	public static final int TIME_FORMAT_ERR         =-900;
	public static final int TIME_YEAR_ERR           =-901;
	public static final int TIME_MONTH_ERR          =-902;
	public static final int TIME_DAY_ERR            =-903;
	public static final int TIME_HOUR_ERR           =-904;
	public static final int TIME_MINUTE_ERR         =-905;
	public static final int TIME_SECOND_ERR         =-906;
	public static final int TIME_WEEK_ERR           =-907;
	public static final int TIME_SET_ERR            =-908;
	public static final int TIME_GET_ERR            =-909;
	public static final int TIME_RAMADDR_OVER       =-910;
	public static final int TIME_RAMLEN_OVER        =-911;

	

//PCI
	public static final int PCI_OK = 0;

	public static final int PCI_Locked_Err = -7000;
	public static final int PCI_KeyType_Err = -7001;
	public static final int PCI_KeyLrc_Err = -7002;
	public static final int PCI_KeyNo_Err = -7003;	
	public static final int PCI_KeyLen_Err = -7004;
	public static final int PCI_KeyMode_Err = -7005;
	public static final int PCI_InputLen_Err = -7006;
	public static final int PCI_InputCancel_Err = -7007;	
	public static final int PCI_InputNotMatch_Err = -7008;	
	public static final int PCI_InputTimeOut_Err = -7009;	
	public static final int PCI_CallTimeInte_Err = -7010;
	public static final int PCI_NoKey_Err = -7011;
	public static final int PCI_WriteKey_Err = -7012;
	public static final int PCI_ReadKey_Err = -7013;	
	public static final int PCI_RsaKeyHash_Err = -7014;
	public static final int PCI_DataLen_Err = -7015;
	public static final int PCI_NoInput_Err = -7016;
	public static final int PCI_AppNumOver_Err = -7017;
	public static final int PCI_ReadMMK_Err = -7020;	
	public static final int PCI_WriteMMK_Err = -7021;	
	public static final int PCI_SignatureVerify_Err = -7030;
	public static final int PCI_RsaKey_Err = -7031;
	public static final int PCI_AuthTimes_Err = -7032;	
	public static final int PCI_KeySame_Err = -7040;	
	
	public static final int PCI_DUKPT_NoKey = -7050;
	public static final int PCI_DUKPT_CounterOverFlow = -7051;	
	public static final int PCI_DUKPT_NoEmptyList = -7052;
	public static final int PCI_DUKPT_InvalidAppNo = -7053;
	public static final int PCI_DUKPT_InvalidKeyID = -7054;
	public static final int PCI_DUKPT_InvalidFutureKeyID = -7055;
	public static final int PCI_DUKPT_InvalidCrc = -7056;	
	public static final int PCI_DUKPT_InvalidBDK = -7057;	
	public static final int PCI_DUKPT_InvalidKSN = -7058;
	public static final int PCI_DUKPT_InvalidMode = -7059;
	public static final int PCI_DUKPT_NotFound = -7060;	
	
//Printer
	public static final int PRINTER_OK = 0;
	public static final int PRINTER_OUT_PAPER = -4002;
	public static final int PRINTER_OVERHEAT = -4005;	
	public static final int PRINTER_NOFONT = -4007;
	public static final int PRINTER_BUFFER_OVERFLOW = -4008;	
	
//MCR
	public static final int MCR_NOTSWIPED  =-3000;

//ICC
	public static final int  ICC_OK                    =0;
	public static final int  ICC_VCCMODEERR             =-2500;  
	public static final int  ICC_INPUTSLOTERR           =-2501;  
	public static final int  ICC_VCCOPENERR             =-2502;  
	public static final int  ICC_ICCMESERR              =-2503;  

	public static final int  ICC_T0_TIMEOUT             =-2200;  
	public static final int  ICC_T0_MORESENDERR         =-2201;  
	public static final int  ICC_T0_MORERECEERR         =-2202;  
	public static final int  ICC_T0_PARERR              =-2203;  
	public static final int  ICC_T0_INVALIDSW           =-2204;  

	public static final int  ICC_DATA_LENTHERR          =-2400;  
	public static final int  ICC_PARERR                 =-2401;  
	public static final int  ICC_PARAMETERERR           =-2402;  
	public static final int  ICC_SLOTERR                =-2403;  
	public static final int  ICC_PROTOCALERR            =-2404;  
	public static final int  ICC_CARD_OUT               =-2405;  
	public static final int  ICC_NO_INITERR             =-2406;  
	public static final int  ICC_ICCMESSOVERTIME        =-2407;  

	public static final int  ICC_ATR_TSERR              =-2100;  
	public static final int  ICC_ATR_TCKERR             =-2101;  
	public static final int  ICC_ATR_TIMEOUT            =-2102;  
	public static final int  ICC_TS_TIMEOUT             =-2115;  
	public static final int  ICC_ATR_TA1ERR             =-2103;  
	public static final int  ICC_ATR_TA2ERR             =-2104;  
	public static final int  ICC_ATR_TA3ERR             =-2105;  
	public static final int  ICC_ATR_TB1ERR             =-2106;  
	public static final int  ICC_ATR_TB2ERR             =-2107;  
	public static final int  ICC_ATR_TB3ERR             =-2108;  
	public static final int  ICC_ATR_TC1ERR             =-2109;  
	public static final int  ICC_ATR_TC2ERR             =-2110;  
	public static final int  ICC_ATR_TC3ERR             =-2111;  
	public static final int  ICC_ATR_TD1ERR             =-2112;  
	public static final int  ICC_ATR_TD2ERR             =-2113;  
	public static final int  ICC_ATR_LENGTHERR          =-2114;  

	public static final int  ICC_T1_BWTERR              =-2300;  
	public static final int  ICC_T1_CWTERR              =-2301;  
	public static final int  ICC_T1_ABORTERR            =-2302;  
	public static final int  ICC_T1_EDCERR              =-2303;  
	public static final int  ICC_T1_SYNCHERR            =-2304;  
	public static final int  ICC_T1_EGTERR              =-2305;  
	public static final int  ICC_T1_BGTERR              =-2306;  
	public static final int  ICC_T1_NADERR              =-2307;  
	public static final int  ICC_T1_PCBERR              =-2308;  
	public static final int  ICC_T1_LENGTHERR           =-2309;  
	public static final int  ICC_T1_IFSCERR             =-2310;  
	public static final int  ICC_T1_IFSDERR             =-2311;  
	public static final int  ICC_T1_MOREERR             =-2312;  
	public static final int  ICC_T1_PARITYERR           =-2313;  
	public static final int  ICC_T1_INVALIDBLOCK        =-2314;  

	public static final int  ICC_ER_DAIN                =-2600;  
	public static final int  ICC_ER_DNIN                =-2601;  
	public static final int  ICC_ER_NOCD                =-2602;  
	public static final int  ICC_ER_SYSF                =-2603;  
	public static final int  ICC_ER_TMOT                =-2604;  
	public static final int  ICC_ER_AFTM                =-2605;  
	public static final int  ICC_ER_INVA                =-2606;  
	public static final int  ICC_ER_PAER                =-2607;  
	public static final int  ICC_ER_FRAM                =-2608;  
	public static final int  ICC_ER_EDCO                =-2609;  
	public static final int  ICC_ER_INFR                =-2610;  
	public static final int  ICC_ER_INFN                =-2611;  
	public static final int  ICC_ER_INDN                =-2612;  
	public static final int  ICC_ER_INPA                =-2613;  
	public static final int  ICC_ER_TOPS                =-2614;  
	public static final int  ICC_ER_INPS                =-2615;  
	public static final int  ICC_ER_DOVR                =-2616;  
	public static final int  ICC_ER_NSFN                =-2617;  
	public static final int  ICC_ER_NSDN                =-2618;  
	public static final int  ICC_ER_NSPR                =-2619;  
	public static final int  ICC_ER_MEMF                =-2620;  

}

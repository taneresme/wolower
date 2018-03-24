CREATE  TABLE "main"."tUsers" 
(
	"ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , 
	"TwitterDisplayName" VARCHAR NOT NULL 
);

CREATE  TABLE "main"."tSocialConnections" 
(
	"ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"tUserID" INTEGER NOT NULL , 
	"SocialMedia" VARCHAR NOT NULL , 
	"SocialUserID" VARCHAR NOT NULL, 
	"ProfileURL" VARCHAR, 
	"ImageURL" VARCHAR, 
	"AccessToken" VARCHAR, 
	"AccessSecret" VARCHAR, 
	"RefreshToken" VARCHAR, 
	"ConnectionDate" DATETIME, 
	"Enabled" INTEGER NOT NULL  DEFAULT 1
);

CREATE  TABLE "main"."tSocialPosts" 
(
	"ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"tUserID" INTEGER NOT NULL , 
	"tSocialConnectionID" INTEGER NOT NULL , 
	"SocialMedia" VARCHAR NOT NULL , 
	"SocialUserID" VARCHAR NOT NULL , 
	"Amount" INTEGER NOT NULL , 
	"Currency" INTEGER NOT NULL , 
	"PostType" VARCHAR NOT NULL , 
	"PostID" VARCHAR NOT NULL , 
	"PostDate" DATETIME NOT NULL , 
	"PostText" VARCHAR NOT NULL , 
	"PostURL" VARCHAR NOT NULL , 
	"AllPostJSON" VARCHAR
);

CREATE  TABLE "main"."tTransactions" 
(
	"ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"tUserID" INTEGER NOT NULL , 
	"tSocialConnectionID" INTEGER NOT NULL , 
	"tPostID_Product" INTEGER NOT NULL , 
	"tPostID_Order" INTEGER NOT NULL , 
	"tMasterpassID" INTEGER NOT NULL , 
	"SocialUserID" VARCHAR NOT NULL , 
	"MasterpassPairingID" VARCHAR NOT NULL , 
	"TransactionTimestamp" DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP, 
	"Amount" INTEGER NOT NULL , 
	"Currency" INTEGER NOT NULL 
);

CREATE  TABLE "main"."tMasterpass" 
(
	"ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"tUserID" INTEGER NOT NULL , 
	"PairingTimestamp" DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP, 
	"OauthToken" VARCHAR, 
	"PairingVerifier" VARCHAR, 
	"PairingToken" VARCHAR, 
	"MpStatus" VARCHAR, 
	"DefaultCardID" VARCHAR, 
	"DefaultCardJSON" VARCHAR, 
	"DefaultShippingAddressID" VARCHAR, 
	"DefaultShippingAddressJSON" VARCHAR, 
	"Enabled" INTEGER NOT NULL  DEFAULT 1
);
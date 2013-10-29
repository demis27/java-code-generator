package org.demis.darempredou.message.email.helper;

import org.demis.darempredou.message.email.EmailHeader;
import org.demis.darempredou.message.email.EmailRecipient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EmailParserHelperTest {


    @Test
    public void parseEmailAddressRecipient() throws Exception {
        String[] froms = {"stephane kermabon <stephane@eptica.com>", "stephane@eptica.com", "stephane kermabon"
                ,"=?UTF-8?Q?St=C3=A9phane_Kermabon?= <demis27@gmail.com>","demis27@gmail.com", "Stéphane Kermabon"
                ,"=?iso-8859-1?Q?Kermabon_St=E9phane?= <kermabon.stephane@yahoo.fr>","kermabon.stephane@yahoo.fr", "Kermabon Stéphane"
        };

        for (int i = 0; i < froms.length; i+= 3) {
            Assert.assertEquals(froms[i + 1], EmailParserHelper.parseEmailAddressRecipient(froms[i]));
        }
    }

    @Test
    public void parseEmailNameRecipient() throws Exception {
        String[] froms = {"stephane kermabon <stephane@eptica.com>", "stephane@eptica.com", "stephane kermabon"
                ,"=?UTF-8?Q?St=C3=A9phane_Kermabon?= <demis27@gmail.com>","demis27@gmail.com", "Stéphane Kermabon"
                ,"=?iso-8859-1?Q?Kermabon_St=E9phane?= <kermabon.stephane@yahoo.fr>","kermabon.stephane@yahoo.fr", "Kermabon Stéphane"
        };

        for (int i = 0; i < froms.length; i+= 3) {
            Assert.assertEquals(froms[i + 2], EmailParserHelper.parseEmailNameRecipient(froms[i]));
        }
    }

    @Test
    public void readHeaderLine() {
        List<EmailHeader> headers = new ArrayList<EmailHeader>();

        EmailParserHelper.readHeaderLine(headers, "Return-Path: <demis27@gmail.com>");
        Assert.assertEquals(headers.size(), 1);
        Assert.assertEquals(headers.get(0).getName(), "Return-Path");
        Assert.assertEquals(headers.get(0).getBinaryValue(), "<demis27@gmail.com>");

        EmailParserHelper.readHeaderLine(headers, "Received: from spool.mail.gandi.net (mspool2-v.mgt.gandi.net [10.0.21.72])");
        EmailParserHelper.readHeaderLine(headers, "    by mboxes1-d.mgt.gandi.net (Postfix) with ESMTP id 4A7074270");
        EmailParserHelper.readHeaderLine(headers, "    for <dwarf-test-extern@demis27.net>; Thu,  7 May 2009 12:50:34 +0200 (CEST)");
        Assert.assertEquals(headers.size(), 2);
        Assert.assertEquals(headers.get(1).getName(), "Received");
        Assert.assertEquals(headers.get(1).getBinaryValue(), "from spool.mail.gandi.net (mspool2-v.mgt.gandi.net [10.0.21.72]) "
                + "by mboxes1-d.mgt.gandi.net (Postfix) with ESMTP id 4A7074270 "
                + "for <dwarf-test-extern@demis27.net>; Thu,  7 May 2009 12:50:34 +0200 (CEST)");
    }

    @Test
    public void getFromRecipient() {
        String[] froms = {"stephane kermabon <stephane@eptica.com>", "stephane@eptica.com", "stephane kermabon"
                ,"=?UTF-8?Q?St=C3=A9phane_Kermabon?= <demis27@gmail.com>","demis27@gmail.com", "Stéphane Kermabon"
                ,"=?iso-8859-1?Q?Kermabon_St=E9phane?= <kermabon.stephane@yahoo.fr>","kermabon.stephane@yahoo.fr", "Kermabon Stéphane"
        };

        for (int i = 0; i < froms.length; i+= 3) {
            EmailRecipient recipient = EmailParserHelper.getRecipient(froms[i], "from");
            Assert.assertEquals(froms[i + 1], recipient.getEmail());
            Assert.assertEquals(froms[i + 2], recipient.getName());
        }
    }

    @Test
    public void getToRecipient() {
        String[] tos = {"dwarf-test-extern@demis27.net","dwarf-test-extern@demis27.net",""
                , "<dwarf-test-extern@demis27.net>", "dwarf-test-extern@demis27.net", ""
        };

        for (int i = 0; i < tos.length; i+= 3) {
            EmailRecipient recipient = EmailParserHelper.getRecipient(tos[i], "to");
            Assert.assertEquals(tos[i + 1], recipient.getEmail());
            Assert.assertNull(recipient.getName());
        }
    }
}

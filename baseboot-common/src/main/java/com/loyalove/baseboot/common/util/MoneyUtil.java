package com.loyalove.baseboot.common.util;


import com.google.common.math.LongMath;
import com.loyalove.baseboot.common.model.Money;
import org.springframework.util.Assert;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtil {
    private static String[] CH = {"", "", "拾", "佰", "仟", "万", "", "", "", "亿", "", "", "", "兆"};

    private static String[] CHS_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public static String format(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("金额不能为null");
        }
        DecimalFormat fmt = new DecimalFormat("##,###,###,###,###.00");
        String result = fmt.format(money.getAmount().doubleValue());
        if (result.indexOf(".") == 0) {
            result = "0" + result;
        }
        return result;
    }

    public static Money round(Money money, Money roundMoney, RoundingMode roundingMode) {
        Assert.notNull(money, "money不能为空");
        Assert.notNull(roundMoney, "roundMoney不能为空");
        Assert.notNull(roundingMode, "roundingMode不能为空");
        long multiple = LongMath.divide(money.getCent(), roundMoney.getCent(), roundingMode);
        long newCent = LongMath.checkedMultiply(multiple, roundMoney.getCent());
        return money.newMoneyWithSameCurrency(newCent);
    }

    public static String getCHSNumber(Money m) {
        if (m == null) {
            throw new IllegalArgumentException("金额不能为null");
        }
        if (m.getCent() == 0L) {
            return "零元";
        }
        String money = m.getAmount().toString();
        String chs = "";

        String tmp_int = money.substring(0, money.indexOf("."));
        String tmp_down = money.substring(money.indexOf(".") + 1, money.length());

        char[] tmp_int_char = tmp_int.toCharArray();
        String[] tmp_chs = new String[tmp_int_char.length];

        int tab = 0;
        for (int i = 0; i < tmp_int_char.length; i++) {
            tab = tmp_int_char.length - i - 1;

            if (tmp_int_char.length <= 5) {
                tmp_chs[tab] = CHS_NUMBER[((int) Float.parseFloat(tmp_int_char[i] + ".0"))];

                if (!tmp_chs[tab].equals("零")) {
                    chs = chs + tmp_chs[tab] + CH[(tmp_int_char.length - i)];
                } else if ((!chs.endsWith("零")) && (tab != 0)) {
                    chs = chs + tmp_chs[tab];
                } else if ((chs.endsWith("零")) && (tab == 0)) {
                    chs = chs.substring(0, chs.length() - 1);
                }

            }

            if ((tmp_int_char.length > 5) && (tmp_int_char.length < 9)) {
                tmp_chs[tab] = CHS_NUMBER[((int) Float.parseFloat(tmp_int_char[i] + ".0"))];

                if (tab >= 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 3)];

                        if (tab == 4) {
                            chs = chs + "万";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 4) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "万";
                            } else {
                                chs = chs + "万";
                            }
                        }

                    }

                }

                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tmp_int_char.length - i)];
                    } else {
                        if ((!chs.endsWith("零")) && (tab != 0)) {
                            chs = chs + tmp_chs[tab];
                        }

                        if ((chs.endsWith("零")) && (tab == 0)) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }

            }

            if ((tmp_int_char.length >= 9) && (tmp_int_char.length <= 12)) {
                tmp_chs[tab] = CHS_NUMBER[((int) Float.parseFloat(tmp_int_char[i] + ".0"))];

                if ((tab >= 8) && (tab < 12)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 7)];

                        if (tab == 8) {
                            chs = chs + "亿";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 8) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "亿";
                            } else {
                                chs = chs + "亿";
                            }
                        }
                    }

                }

                if ((tab >= 4) && (tab < 8)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 3)];

                        if (tab == 4) {
                            chs = chs + "万";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 4) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);

                                if (!chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            } else if (!chs.endsWith("亿")) {
                                chs = chs + "万";
                            }
                        }

                    }

                }

                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tmp_int_char.length - i)];
                    } else {
                        if ((!chs.endsWith("零")) && (tab != 0)) {
                            chs = chs + tmp_chs[tab];
                        }

                        if ((chs.endsWith("零")) && (tab == 0)) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }

            }

            if ((tmp_int_char.length > 12) && (tmp_int_char.length <= 16)) {
                tmp_chs[tab] = CHS_NUMBER[((int) Float.parseFloat(tmp_int_char[i] + ".0"))];

                if ((tab >= 12) && (tab < 16)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 11)];

                        if (tab == 12) {
                            chs = chs + "兆";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 12) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "兆";
                            } else {
                                chs = chs + "兆";
                            }
                        }
                    }
                }

                if ((tab >= 8) && (tab < 12)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 7)];

                        if (tab == 8) {
                            chs = chs + "亿";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 8) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            } else if (!chs.endsWith("兆")) {
                                chs = chs + "亿";
                            }
                        }
                    }

                }

                if ((tab >= 4) && (tab < 8)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 3)];

                        if (tab == 4) {
                            chs = chs + "万";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 4) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);

                                if ((!chs.endsWith("亿")) &&
                                        (!chs.endsWith("兆"))) {
                                    chs = chs + "万";
                                }
                            } else if ((!chs.endsWith("亿")) &&
                                    (!chs.endsWith("兆"))) {
                                chs = chs + "万";
                            }
                        }

                    }

                }

                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tmp_int_char.length - i)];
                    } else {
                        if ((!chs.endsWith("零")) && (tab != 0)) {
                            chs = chs + tmp_chs[tab];
                        }

                        if ((chs.endsWith("零")) && (tab == 0)) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }

            }

            if (tmp_int_char.length > 16) {
                tmp_chs[tab] = CHS_NUMBER[((int) Float.parseFloat(tmp_int_char[i] + ".0"))];

                if (tab >= 12) {
                    chs = chs + tmp_chs[tab];

                    if (tab == 12) {
                        chs = chs + "兆";
                    }

                }

                if ((tab >= 8) && (tab < 12)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 7)];

                        if (tab == 8) {
                            chs = chs + "亿";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 8) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            } else if (!chs.endsWith("兆")) {
                                chs = chs + "亿";
                            }
                        }
                    }

                }

                if ((tab >= 4) && (tab < 8)) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tab - 3)];

                        if (tab == 4) {
                            chs = chs + "万";
                        }

                    } else {
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }

                        if (tab == 4) {
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);

                                if (!chs.endsWith("兆")) {
                                    if (!chs.endsWith("亿")) {
                                        chs = chs + "万";
                                    }
                                }
                            } else if (!chs.endsWith("兆")) {
                                if (!chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }

                }

                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[(tmp_int_char.length - i)];
                    } else {
                        if ((!chs.endsWith("零")) && (tab != 0)) {
                            chs = chs + tmp_chs[tab];
                        }

                        if ((chs.endsWith("零")) && (tab == 0)) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }
            }
        }

        char[] tmp = tmp_down.toCharArray();
        if (tmp.length == 1) {
            if (tmp[0] != '0')
                chs = chs + "元" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[0]).append(".0").toString()))] + "角整";
            else
                chs = chs + "元整";
        } else if ((tmp[1] != '0') && (tmp[0] != '0')) {
            if (chs.isEmpty()) {
                chs = CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[0]).append(".0").toString()))] + "角" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[1]).append(".0").toString()))] + "分";
            } else {
                chs = chs + "元" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[0]).append(".0").toString()))] + "角" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[1]).append(".0").toString()))] + "分";
            }
        } else if ((tmp[1] != '0') && (tmp[0] == '0')) {
            if (chs.isEmpty())
                chs = CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[1]).append(".0").toString()))] + "分";
            else
                chs = chs + "元零" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[1]).append(".0").toString()))] + "分";
        } else if ((tmp[1] == '0') && (tmp[0] != '0')) {
            if (chs.isEmpty())
                chs = CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[0]).append(".0").toString()))] + "角";
            else
                chs = chs + "元" + CHS_NUMBER[((int) Float.parseFloat(new StringBuilder().append(tmp[0]).append(".0").toString()))] + "角";
        } else if ((tmp[1] == '0') && (tmp[0] == '0')) {
            chs = chs + "元整";
        }

        return chs;
    }

    public static Money add(Money money1, Money money2) {
        return money1.add(money2);
    }


    public static Money subtract(Money money1, Money money2) {
        return money1.subtract(money2);
    }

    public static Money multiply(Money money1, long multiply) {
        return money1.multiply(multiply);
    }


    public static Money divide(Money money1, long divide) {
        return money1.divide(divide);
    }

}
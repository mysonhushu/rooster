/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster.api;

import java.util.Comparator;
import rooster.internal.bean.t_daily_attendance;

 
public class SortByCardNo implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        t_daily_attendance  tda1 = (t_daily_attendance) o1;
        t_daily_attendance  tda2 = (t_daily_attendance) o2;
        int cardNo1 = Integer.parseInt(tda1.card_no);
        int cardNo2 = Integer.parseInt(tda2.card_no);
        if(cardNo1>cardNo2)
        {
            return 1;
        }else{
            return 0;
        }
    }
}

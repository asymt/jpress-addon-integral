#namespace("integral")
    #sql("queryAvailablesIntegralSum")
        select sum(integral) from integral_availables where user_id = #para(0)
    #end
    #sql("queryUserIntegral")
        select integral from user_integral where user_id = #para(0)
    #end
    #sql("updateAllUserIntegral")
        replace into user_integral(user_id,integral) select user_id,sum(integral) from integral_availables group by user_id
    #end
#end

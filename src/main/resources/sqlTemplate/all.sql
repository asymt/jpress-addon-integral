#namespace("integral")
    #sql("queryUserIntegral")
        select integral from user_integral where user_id = #para(0)
    #end
#end
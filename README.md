#DriveTrain
Base基础包必须购买的 photoexamFee=0.01, km1=0.01, km2=0.01, km3=0.01, km4=0.01,hoursCardFee=0.01,
Base = photoexamFee + hoursCardFee + km1 + km2 + km3 + km4
一口价 all_pay服务端返回
分期付：
第一期 Base + installment1
第二期：installment2
第三期：installment3

服务端返回的
getServicesByUserId
HaveService中

Base和Km1为true表示分期一 其他都为false

Km2为true 表示分期二 其他都为false

Km3和Km4为true表示分期三 其他全为false

全为true表示一口价，
TrainingShow.class中使用了TextView显示图片
PostList.class中使用了Emoji表情，使用


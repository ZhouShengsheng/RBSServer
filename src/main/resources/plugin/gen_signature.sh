#! /bin/sh

id=10001
nonceStr=fjdksiantwsdkazl
timestamp=1461901007
str="id=${id}&nonceStr=${nonceStr}&timestamp=${timestamp}"

echo -n $str | shasum

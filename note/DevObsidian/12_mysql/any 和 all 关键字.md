```sql
A = any('a','b') 等价于 A = 'a' or A = 'b'

A = all('a','b') 等价于 A = 'a' and A = 'b'
```
总结 ：any 相当于用or链接后面括号里的子元素，all 相当于用and链接后面括号里面的子元素
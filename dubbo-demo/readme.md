# dubbo
## ��ʲô��
> �ṩ�ֲ�ʽ���������������ṩһ����̬����ʵ�ֶ�Э���ע�����Ķ��������ĵȵȵ�֧�֡�

## Ϊʲô��
> �ֲ�ʽ����ģʽ�ĳ��죬�ǵ�Խ��Խ���ԭ���ĵ��������ֲ�ʽ����չ���ڷֲ�ʽ�Ļ����Ϊ����������и߿��õ����ԣ���Ҫ�Լ�Ⱥ�ķ�����з�������ʵ�ָ��ؾ��⣬�ݴ��۶ϣ�崻��ָ�������bubbo���ǿ����ṩ��Щ�����һ�ֽ����������̬��

## ��ô��
��������(dubboĬ�Ͻ��Ὺ��20880�Ķ˿ڣ�zkĬ�ϼ���2181�˿�),��Ϊ������ṩ��(Provider)�ͷ����������(Consumer)

## ���������ļ�
### provider
```xml
<!-- �����Ҫ������ע��������ʾ������֮���������ϵ -->
<dubbo:application name="applicationName"/>

<dubbo:registry protocol="" address=""/>

<dubbo:service interface="" ref=""/>

<bean id="" class=""/>

```

### consumer
```xml
<dubbo:registry protocol="" address=""/>
<!-- ����Զ��provider�˵����� -->
<dubbo:reference id="" interface=""/>

```

## ע������
`@Service`

`@Reference`
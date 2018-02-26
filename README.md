# activitiOA
使用activiti工作流引擎做OA系统中的请假申请
（印章申请后续添加）

## 项目架构
SSM (spring + springMVC + mybatis)

com.cypher.activiti.controller - 控制层<br/>
com.cypher.activiti.core - 核心<br/>
com.cypher.activiti.dao - dao层<br/>
com.cypher.activiti.dto - 数据传输模型层<br/>
com.cypher.activiti.mapping - mybatis映射文件<br/>
com.cypher.activiti.model - 数据模型层 entity<br/>
com.cypher.activiti.service - 处理业务逻辑接口<br/>
com.cypher.activiti.service.impl - 处理业务逻辑实现类<br/>
com.cypher.activiti.util - 工具类<br/>
com.cypher.activiti.vo - 视图模型层<br/>

项目代码所在目录：\code\activitiOA （目前使用jsp做前端显示，未做前后端分离）<br/>
数据库初始化文件所在目录：\doc\初始化数据库表<br/>
activiti 数据库初始化：运行com.cypher.activiti.cfg.activiti.ActivitiGenerateTest 下方法testActivitiEngineFromResource即可<br/>

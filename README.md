# fisc-demo

與學校的產學合作課程，利用 Spring Boot 架構所建立的金融相關的後台管理應用

資料庫使用 H2 Database

套件管理使用 Maven

<hr>
<h5>預設資料庫使用者</h5>
<p>帳號: kenny 密碼: 1qaz@WSX</p>
<p>帳號: steve 密碼: 1qaz@WSX</p>
<p>帳號: Mary 密碼: 111111</p>
<hr>
<h5>預設資料庫 role 中的對應 FUNCTIONS</h5>
<p>名稱: Teller 選單功能權限: system.users / system.roles / fisc.banks</p>
<p>名稱: Supervisor 選單功能權限: system.users / system.roles / system.permissions</p>
<hr>

<h5>完成項目說明</h5>
<p>1. 新增功能: 銀行檔維護 (新增/刪除/修改/查詢)</p>
<p>2. 新增功能: 使用者維護 (新增/刪除/修改/查詢) Hint: 密碼不可以存明碼於資料庫中</p>

`其中 roleId 使用下拉選單方式填寫，避免 roleId 填寫錯誤`

<p>3. 使登入頁面可以使用新增的使用者登入</p>
<p>4. 修改預設的使用者，預設新增一個使用者 Mary ，密碼使用111111，其餘隨意</p>
<p>5. 額外加分題:</p>
<p>5-1. TopMenu 功能選單從資料庫的 SYS_FUNC 的功能清單中，對應 SYS_ROLE 中的 Functions 來顯示於 TopMenu 上</p>
<p>5-2. 新增權限維護功能，增加功能清單欄位，可以勾選不同的功能列表</p>

`勾選不同的權限後，所看到的 TopMenu 清單也會不同`

`新創建的 role 功能清單預設為全部開啟`

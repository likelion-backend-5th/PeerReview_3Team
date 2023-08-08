# MiniProject_Basic_YuChaeyeon

### ♻️멋사마켓
🥕 당근마켓, 중고나라 등을 착안하여 중고 제품 거래 플랫폼을 만들어보는 미니 프로젝트

---

### 📇 멋사마켓 ERD
![image](https://github.com/likelion-backend-5th/MiniProject_Basic_YuChaeyeon/assets/52392720/3377bc11-132a-4e9d-a289-86a87fc5ae3f)

### 📌 요구사항
1. 중고 물품 관리 요구사항
>  + 누구든지 중고 거래를 목적으로 물품에 대한 정보를 등록할 수 있다.
>    * 이때 반드시 포함되어야 하는 내용은 **제목, 설명, 최소 가격, 작성자**이다.
>    * 또한 사용자가 물품을 등록할 때, 비밀번호 항목을 추가해서 등록한다.
>    * 최초로 물품이 등록될 때, 중고 물품의 상태는 판매중 상태가 된다.
>  + 등록된 물품 정보는 누구든지 열람할 수 있다.
>    * 페이지 단위 조회가 가능하다.
>    * 전체 조회, 단일 조회 모두 가능하다.
>  + 등록된 물품 정보는 수정이 가능하다.
>    * 이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.
>  + 등록된 물품 정보에 이미지를 첨부할 수 있다.
>    * 이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.
>    * 이미지를 관리하는 방법은 자율이다.
>  + 등록된 물품 정보는 삭제가 가능하다.
>    * 이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.
>  + End Point
>    * POST /items 
```json
{
    "title": "중고 맥북 팝니다",
    "description": "2019년 맥북 프로 13인치 모델입니다",
    "minPriceWanted": 1000000,
    "writer": "jeeho.dev",
    "password": "1qaz2wsx"
}
```

>    * GET /items?page={page}&limit={limit}

>    * GET /items/{itemId}
```json
{
		"title": "중고 맥북 팝니다",
		"description": "2019년 맥북 프로 13인치 모델입니다",
		"minPriceWanted": 1000000,
    "status": "판매중"
}
```

>    * PUT /items/{itemId}
```json
{
    "title": "중고 맥북 팝니다",
    "description": "2019년 맥북 프로 13인치 모델입니다",
    "minPriceWanted": 1000000,
    "writer": "jeeho.dev",
    "password": "1qaz2wsx"
}
```

>    * DELETE /items/{itemId}
```json
{
    "writer": "jeeho.dev",
    "password": "1qaz2wsx"
}
```

2. 중고 물품 댓글 요구사항
>  + 등록된 물품에 대한 질문을 위하여 댓글을 등록할 수 있다.
>    * 이때 반드시 포함되어야 하는 내용은 대상 물품, 댓글 내용, 작성자이다.
>    * 또한 댓글을 등록할 때, 비밀번호 항목을 추가해서 등록한다.
>  + 등록된 댓글은 누구든지 열람할 수 있다.
>    * 페이지 단위 조회가 가능하다.
>  + 등록된 댓글은 수정이 가능하다.
>    * 이때, 댓글이 등록될 때 추가한 비밀번호를 첨부해야 한다.
>  + 등록된 물품 정보는 삭제가 가능하다.
>    * 이때, 댓글이 등록될 때 추가한 비밀번호를 첨부해야 한다.
>  + 댓글에는 초기에 비워져 있는 답글 항목이 존재한다. 
>    * 만약 댓글이 등록된 대상 물품을 등록한 사람일 경우, 물품을 등록할 때 사용한 비밀번호를 첨부할 경우 답글 항목을 수정할 수 있다.
>    * 답글은 댓글에 포함된 공개 정보이다.
>  + End Point
>    * POST /items/{itemId}/comments
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234",
    "content": "할인 가능하신가요?"
}
```

>    * GET /items/{itemId}/comments

>    * PUT /items/{itemId}/comments/{commentId}
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234",
    "content": "할인 가능하신가요? 1000000 정도면 고려 가능합니다"
}
```

>    * DELETE /items/{itemId}/comments/{commentId}
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234"
}
```

3. 구매 제안 요구사항
>  + 등록된 물품에 대하여 구매 제안을 등록할 수 있다.
>    * 이때 반드시 포함되어야 하는 내용은 대상 물품, 제안 가격, 작성자이다.
>    * 또한 구매 제안을 등록할 때, 비밀번호 항목을 추가해서 등록한다.
>    * 구매 제안이 등록될 때, 제안의 상태는 제안 상태가 된다.
>  + 구매 제안은 대상 물품의 주인과 등록한 사용자만 조회할 수 있다.
>    * 대상 물품의 주인은, 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다. 이때 물품에 등록된 모든 구매 제안이 확인 가능하다. 페이지 기능을 지원한다.
>  + 등록한 사용자는, 조회를 위해서 자신이 사용한 작성자와 비밀번호를 첨부해야 한다. 이때 자신이 등록한 구매 제안만 확인이 가능하다. 페이지 기능을 지원한다.
>  + 등록된 제안은 수정이 가능하다.
>    * 이때, 제안이 등록될때 추가한 작성자와 비밀번호를 첨부해야 한다.
>  + 등록된 제안은 삭제가 가능하다.
>    * 이때, 제안이 등록될때 추가한 작성자와 비밀번호를 첨부해야 한다.
>  + 대상 물품의 주인은 구매 제안을 수락할 수 있다. 
>    * 이를 위해서 제안의 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.
>    * 이때 구매 제안의 상태는 수락이 된다.
>  + 대상 물품의 주인은 구매 제안을 거절할 수 있다. 
>    * 이를 위해서 제안의 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.
>    * 이때 구매 제안의 상태는 거절이 된다.
>  + 구매 제안을 등록한 사용자는, 자신이 등록한 제안이 수락 상태일 경우, 구매 확정을 할 수 있다. 
>    * 이를 위해서 제안의 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.
>    * 이때 구매 제안의 상태는 확정이 된다.
>    * 구매 제안이 확정될 경우, 대상 물품의 상태는 판매 완료가 된다.
>    * 구매 제안이 확정될 경우, 확정되지 않은 다른 구매 제안의 상태는 모두 거절이 된다.
>  + End Point
>    * POST /items/{itemId}/proposals
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234",
    "suggestedPrice": 1000000
}
```
>    * GET /items/{itemId}/proposals?writer=jeeho.edu&password=qwerty1234&page=1

>    * PUT /items/{itemId}/proposals/{proposalId}
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234",
    "suggestedPrice": 1100000
}
```

>    * DELETE /items/{itemId}/proposals/{proposalId}
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234"
}
```

>    * PUT /items/{itemId}/proposals/{proposalId}
```json
{
    "writer": "jeeho.dev",
    "password": "1qaz2wsx",
    "status": "수락" || "거절"
}
```

>    * PUT /items/{itemId}/proposals/{proposalId}
```json
{
    "writer": "jeeho.edu",
    "password": "qwerty1234"
    "status": "확정"
}
```







<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 정보 수정</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
</head>
<style>
    body {
        font-family: 'Noto Sans KR', sans-serif;
        background-color: #F5F6FA;
        margin: 0;
        padding: 0;
    }

    .mypage-container {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        padding: 20px;
        max-width: 1200px;
        margin: 0 auto;
    }

    /* 프로필 섹션 */
    .profile-section {
        background-color: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        text-align: center;
        width: 300px;
        margin-right: 20px;
    }

    .edit-profile-btn {
        padding: 10px 20px;
        background-color: #2ECC71;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 1rem;
        cursor: pointer;
    }

    .edit-profile-btn:hover {
        background-color: #27AE60;
    }

    /* 정보 섹션 */
    .info-section {
        flex: 1;
        background-color: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    .section-title {
        font-size: 1.5rem;
        color: #101E4E;
        border-bottom: 2px solid #E9F0FA;
        padding-bottom: 10px;
        margin-bottom: 20px;
    }

    .info-table {
        width: 100%;
        border-collapse: collapse;
    }

    .info-table th, .info-table td {
        padding: 15px;
        text-align: left;
        border-bottom: 1px solid #E9F0FA;
    }

    .info-table th {
        color: #737373;
        font-weight: normal;
    }

    .info-table td {
        color: #101E4E;
    }

    .info-table input {
        width: 100%;
        padding: 10px;
        border: 1px solid #D1D5DB;
        border-radius: 5px;
        font-size: 1rem;
        box-sizing: border-box;
    }

    .delete-btn {
        padding: 10px 20px;
        background-color: red;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 1rem;
        cursor: pointer;
    }

    .delete-btn:hover {
        background-color: #C0392B;
    }
</style>
<body>
<div class="mypage-container">
    <!-- 프로필 섹션 -->
    <div class="profile-section">
        <button class="delete-btn">계정 삭제</button>
    </div>

    <!-- 회원 정보 섹션 -->
    <div class="info-section">
        <h2 class="section-title">회원 정보</h2>
        <form action="${contextPath}/member.update" name="updateForm" method="post"
              enctype="multipart/form-data" onsubmit="return updateCheck();">
            <table class="info-table">
                <tr>
                    <th>아이디</th>
                    <td>
                        <!-- `readonly`를 사용하여 사용자 입력은 막지만 값은 서버로 전달되도록 설정 -->
                        <input value="${sessionScope.loginMember.bm_id}" name="bm_id" placeholder="ID" autocomplete="off" maxlength="100" readonly>
                        <input type="hidden" name="bm_id" value="${sessionScope.loginMember.bm_id}">
                    </td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_pw}" name="bm_pw" placeholder="PASSWORD" autocomplete="off" maxlength="100" type="password">
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_name}" name="bm_name" placeholder="이름" autocomplete="off" maxlength="100">
                    </td>
                </tr>
                <tr>
                    <th>닉네임</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_nickname}" name="bm_nickname" placeholder="닉네임" autocomplete="off" maxlength="100">
                    </td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_phoneNum}" name="bm_phoneNum" placeholder="전화번호" autocomplete="off" maxlength="14">
                    </td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_address}" name="bm_address" placeholder="주소" autocomplete="off" maxlength="100">
                    </td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_birthday}" name="bm_birthday" placeholder="생년월일" autocomplete="off" maxlength="10">
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>
                        <input value="${sessionScope.loginMember.bm_mail}" name="bm_mail" placeholder="이메일" autocomplete="off" maxlength="50">
                    </td>
                </tr>
            </table>
            <button class="edit-profile-btn" type="submit" id="login_update">프로필 수정</button>
        </form>
    </div>
</div>

</body>
</html>

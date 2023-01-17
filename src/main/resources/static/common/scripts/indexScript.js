window.onload = () => {
    setNavBarComponent();
}

const setNavBarComponent = () => {
    if(window.sessionStorage.getItem("userSessionData")){
        // 로그인 정보 있는 경우
        const loginTrue = document.querySelector(".div-menubar-login-state").insertAdjacentHTML(
            "afterbegin",
            `<span id="span-menubar-logout"><a href="/logout">로그아웃</a></span>
            <span id="span-menubar-mypage"><a href="/mypage">마이페이지</a></span>`
        );


    }else{ // 로그인 정보 없는 경우
        const loginTrue = document.querySelector(".div-menubar-login-state").insertAdjacentHTML(
            "afterbegin",
            `<span id="span-menubar-login"><a href="/login">로그인</a></span>
            <span id="span-menubar-signup"><a href="/signup">회원가입</a></span>`
        );
    }
}
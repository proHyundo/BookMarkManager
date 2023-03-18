window.onload = () => {
   console.log('abc');

   const BtnPostLoginInput = document.getElementById("button-submit-login-input");
   BtnPostLoginInput.addEventListener('click', () => {
      fetch("/login/request", {
         method: "POST",
         headers: {
            'content-type' : 'application/json'
         },
         body: JSON.stringify({
            "email": document.getElementById("input-value-email").value,
            "pwd": document.getElementById("input-value-pwd").value
         }),


      }).then(response =>{
       if(!response.ok){
          alert('NOT OK');
          console.log("response : "+response);
          console.log("response.status : "+response.status);
          console.log("response.statusText : "+response.statusText);
          console.log("response.headers : "+response.headers);
          // 여기에서는 어떤 제어가 가능한지 아직 모르겠다.
       }
       return response.json()
      }).then((data) => {
         if(data.statusCode == '200'){
            // 세션스토리지 저장
            window.localStorage.setItem("userData", data);
            alert('로그인 성공');
            // 홈페이지로 이동
            location.href="/";
         }else if(data.statusCode == '401'){
            alert('잘못된 ID 또는 PWD 입니다. 입력을 다시 확인해주세요.');
         }else if(data.statusCode == '404'){
            alert('ID 또는 PWD 가 올바르지 않습니다.');
         }
      })
   });

}
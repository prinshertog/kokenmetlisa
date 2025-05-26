<script lang="ts">
    const BACKEND_URL = "http://localhost:8080";
    import { goto } from "$app/navigation";

    let formData = {
        username: "",
        password: ""
    }

    let message = ""

    export async function login(event: SubmitEvent) {
        event.preventDefault();
        try {
            const response = await fetch(BACKEND_URL + "/login", {
                method: "POST",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                const token = await response.text()
                document.cookie = `username=${formData.username}; path=/; max-age=3600`;
                localStorage.setItem('bearer', token)
                goto("/admin")
            } else {
                message = await response.json()
                message = message.error
            }
        } catch (error) {
            console.error("Login failed " + error)
        }
    }
</script>

<div class="flex justify-center flex-col items-center">
    <form class="flex flex-col border-1 p-5" onsubmit={login} method="POST">
        <input required bind:value={formData.username} type="text" placeholder="username">
        <input required bind:value={formData.password} type="password" placeholder="password">
        <input class="hover:cursor-pointer" type="submit" value="Login">
    </form>
    <p>{message}</p>
</div>
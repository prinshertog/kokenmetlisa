<script lang="ts">
    const { data, form } = $props();
    const { currentUser, isAdmin, users } = data;
</script>

<div class="min-h-screen bg-gray-50">
    <header class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 class="text-2xl font-semibold text-gray-900">User Settings</h1>
            <div class="flex items-center space-x-4">
                <a href="/dashboard" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Dashboard
                </a>
                <form action="?/logout" method="POST" class="inline">
                    <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </header>
    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <!-- Change Password Section -->
        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-xl font-semibold mb-4">Change Password</h2>
            <form method="POST" action="?/changePassword" class="space-y-4">
                <input type="hidden" name="username" value={currentUser}>
                <div>
                    <input class="w-full px-3 py-2 border rounded-md" 
                        type="password" name="oldPassword" 
                        placeholder="Current Password" required>
                </div>
                <div>
                    <input class="w-full px-3 py-2 border rounded-md" 
                        type="password" name="newPassword" 
                        placeholder="New Password" required>
                </div>
                <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700">
                    Change Password
                </button>
            </form>
        </div>

        {#if isAdmin}
            <!-- Admin User Management Section -->
            <div class="bg-white shadow rounded-lg p-6">
                <h2 class="text-xl font-semibold mb-4">User Management</h2>
                
                <!-- Create User Form -->
                <form method="POST" action="?/createUser" class="space-y-4 mb-8">
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" 
                            type="text" name="username" 
                            placeholder="Username" required>
                    </div>
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" 
                            type="password" name="password" 
                            placeholder="Password" required>
                    </div>
                    <div>
                        <select class="w-full px-3 py-2 border rounded-md" 
                            name="role" required>
                            <option value="USER">User</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                    </div>
                    <button class="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700">
                        Create User
                    </button>
                </form>

                <!-- Users List -->
                <div class="space-y-2">
                    {#each users as {username, role}}
                        <div class="flex justify-between items-center p-2 bg-gray-50 rounded">
                            <span>{username} ({role})</span>
                            <form method="POST" action="?/deleteUser" class="inline">
                                <input type="hidden" name="username" value={username}>
                                <button class="text-red-600 hover:text-red-800"
                                    disabled={username === currentUser}>
                                    Delete
                                </button>
                            </form>
                        </div>
                    {/each}
                </div>
            </div>
        {/if}

        {#if form?.error}
            <div class="mt-4 p-4 bg-red-50 text-red-800 rounded-md">
                {form.error}
            </div>
        {:else if form?.success}
            <div class="mt-4 p-4 bg-green-50 text-green-800 rounded-md">
                Operation completed successfully!
            </div>
        {/if}
    </main>
</div>

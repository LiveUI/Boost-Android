package io.liveui.boost.api

import android.app.Application
import io.liveui.boost.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class MockResponseResolverTest {

    var application: Application = mock(Application::class.java)

    @Test
    fun normalizeUrl() {
        var mockResponseResolver = MockResponseResolver(application)

        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"info", "GET"), "GET/info.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"auth", "POST"), "POST/auth.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"token", "POST"), "POST/token.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams", "GET"), "GET/teams.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams", "POST"), "POST/teams.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/check", "POST"), "POST/teams/check.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/123", "GET"), "GET/teams/0.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/123", "PUT"), "PUT/teams/0.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/123/users", "GET"), "GET/teams/0/users.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/123/link", "POST"), "POST/teams/0/link.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/123/unlink", "POST"), "POST/teams/0/unlink.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"teams/79EF2805-D627-4354-916B-AD8A037DF11B/apps/overview", "GET"), "GET/teams/0/apps/overview.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"apps?tags=tags", "POST"), "POST/apps_tags.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"apps/123", "GET"), "GET/apps/0.json")
        assertEquals(mockResponseResolver.normalizeUrl(BuildConfig.URL[0]+"apps/123/auth", "GET"), "GET/apps/0/auth.json")
    }

}
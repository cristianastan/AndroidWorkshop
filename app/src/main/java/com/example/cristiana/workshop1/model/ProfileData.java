package com.example.cristiana.workshop1.model;

/**
 * Created by Cristiana on 4/11/2017.
 */

public class ProfileData {
    private String mName;
    private String mOrganization;
    private String mBio;
    private String mImageUrl;
    private String mLocation;
    private String mEmail;
    private String mCreated;
    private String mUpdated;
    private int mPublicRepos;
    private int mPrivateRepos;

    public static ProfileData sMockProfileData;

    public ProfileData() {
    }

    public ProfileData(String name, String organization, String bio, String imageUrl, String location, String email, String created, String updated, int publicRepos, int privateRepos) {
        mName = name;
        mOrganization = organization;
        mBio = bio;
        mImageUrl = imageUrl;
        mLocation = location;
        mEmail = email;
        mCreated = created;
        mUpdated = updated;
        mPublicRepos = publicRepos;
        mPrivateRepos = privateRepos;
    }

    static {
        sMockProfileData = new ProfileData(
                "Cristiana Stan",
                "UPB",
                "I am a Senior Android Cat-gineer with six years worth of experience in developing Android applications. My passion always resided in creating beautiful applications, and researching for new ways to improve user eperience for mobile devices. I see myself as an effective leader, skilled in enlisting the support of my team members so they are aligned with the project\\'s and organisation\\'s goals. Using a meticulous and detail-oriented approach, I am able to prioritise and delegate tasks effectively, to ensure project completion under the best of terms",
                "drawable/cat",
                "Bucharest",
                "cristianastan95@gmail.com",
                "Wed, Mar 29, 2015",
                "Tue, Apr 11, 2017",
                12,
                10
        );
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public int getPublicRepos() {
        return mPublicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        mPublicRepos = publicRepos;
    }

    public int getPrivateRepos() {
        return mPrivateRepos;
    }

    public void setPrivateRepos(int privateRepos) {
        mPrivateRepos = privateRepos;
    }
}

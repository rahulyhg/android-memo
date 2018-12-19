package me.dara.memoapp.network.model;

import org.jetbrains.annotations.NotNull;

/**
 * @author sardor
 */
public final class User {
   @NotNull
   private final String email;
   @NotNull
   private final String password;
   @NotNull
   private final String photoUrl;
   private final boolean gender;
   private final int age;

   @NotNull
   public final String getEmail() {
      return this.email;
   }

   @NotNull
   public final String getPassword() {
      return this.password;
   }

   @NotNull
   public final String getPhotoUrl() {
      return this.photoUrl;
   }

   public final boolean getGender() {
      return this.gender;
   }

   public final int getAge() {
      return this.age;
   }

   public User(@NotNull String email, @NotNull String password, @NotNull String photoUrl, boolean gender, int age) {
      this.email = email;
      this.password = password;
      this.photoUrl = photoUrl;
      this.gender = gender;
      this.age = age;
   }

   public User() {
      this("", "", "", false, 0);
   }
}